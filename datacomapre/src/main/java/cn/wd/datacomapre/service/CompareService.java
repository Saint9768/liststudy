package cn.wd.datacomapre.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xzhou.Saint
 * @version 1.0
 * @date 2020/7/20 9:48
 */
@Slf4j
@Component
public class CompareService {

    /**
     * 主库文件中读取出的数据存储的队列
     */
    LinkedBlockingQueue<String> originalQueue = new LinkedBlockingQueue<>(10000);

    /**
     * 二级库文件中读取出的数据存储的队列
     */
    LinkedBlockingQueue<String> secondQueue = new LinkedBlockingQueue<>(10000);

    /**
     * 写入更新文件的队列
     */
    LinkedBlockingQueue<String> needUpdate = new LinkedBlockingQueue<>(10000);

    /**
     * 写入插入文件的队列
     */
    LinkedBlockingQueue<String> needInsert = new LinkedBlockingQueue<>(1000);

    /**
     * 写入删除文件的队列
     */
    LinkedBlockingQueue<String> needDelete = new LinkedBlockingQueue<>(1000);

    /**
     * 读取主库文件流
     */
    BufferedReader originalIn = null;

    /**
     * 读取二级库文件流
     */
    BufferedReader secondIn = null;

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(7);


    /**
     * 比较主库和二级库的 OB_OBJECT_ID 和 RP_GEN_DATETIME。将取出的行数据进行字符串拆分，封装成Map<String id，String datetime>。
     * 如果从队列中取出的map一样，则从两个同步队列中删除此条数据。
     * 如果original队列的行数据小于
     */
    public void compare() {

        try {
            //从主库队列中取数据
            String originalInfo = null;

            //从二级库队列中取数据
            String secondInfo = null;

            //从队列中读取数据
            while (true) {
                //直到从主库队列中取出数据，才进行下一步
                while (originalInfo == null) {
                    originalInfo = originalQueue.poll();
                }

                //直到从二级库队列中取出数据，才进行下一步
                while (secondInfo == null) {
                    secondInfo = secondQueue.poll();
                }
                if ("end".equals(originalInfo) && "end".equals(secondInfo)) {
                    //给插入/更新/删除队列添加结束标志。
                    needInsert.offer("end");
                    needDelete.offer("end");
                    needUpdate.offer("end");
                    log.error("主库和二级库数据对比结束！");
                    break;
                } else if ("end".equals(originalInfo) && !"end".equals(secondInfo)) {
                    //主库的数据对比完成，将二级库剩余的数据全部插入到 待删除队列 中
                    log.error("主库数据对比结束！");

                    //截取字符串中的id信息
                    int index = secondInfo.indexOf('$');
                    String secondId = secondInfo.substring(0, index);
                    //主库队列数据取完了
                    //判断插入队列是否失败
                    boolean cont = false;
                    while (!cont) {
                        //插入队列失败则等待100ms，返回false。然后重试
                        cont = needDelete.offer(secondId, 100, TimeUnit.MILLISECONDS);
                    }
                    secondInfo = secondQueue.poll();
                    continue;
                } else if ("end".equals(secondInfo) && !"end".equals(originalInfo)) {
                    //二级库的数据对比完成，将主库剩余的数据全部插入到 待插入队列 中
                    log.error("二级库数据对比结束！");

                    //截取字符串中的id信息
                    int index = originalInfo.indexOf('$');
                    String originalId = originalInfo.substring(0, index);
                    //主库队列数据取完了
                    //判断插入队列是否失败
                    boolean cont = false;
                    while (!cont) {
                        //插入队列失败则等待100ms，返回false。然后重试
                        cont = needInsert.offer(originalId, 100, TimeUnit.MILLISECONDS);
                    }
                    originalInfo = originalQueue.poll();
                    continue;
                } else {
                    //截取id 和 datetime
                    //截取字符串中的id信息
                    int index1 = originalInfo.indexOf('$');
                    String originalId = originalInfo.substring(0, index1);
                    String originalDatetime = originalInfo.substring(index1 + 1);

                    int index2 = secondInfo.indexOf('$');
                    String secondId = secondInfo.substring(0, index2);
                    String secondDatetime = secondInfo.substring(index2 + 1);
                    //两个队列中的数据相同
                    if (originalInfo.equals(secondInfo)) {
                        originalInfo = originalQueue.poll();
                        secondInfo = secondQueue.poll();
                        continue;
                    } else if (originalId.compareTo(secondId) < 0) {

                        //主库id小于二级库id，说明二级库缺数据，需要插入
                        //判断插入队列是否失败
                        boolean cont = false;
                        while (!cont) {
                            //插入队列失败则等待100ms，返回false。然后重试
                            cont = needInsert.offer(originalId, 500, TimeUnit.MILLISECONDS);
                        }
                        //继续取主库
                        originalInfo = originalQueue.poll();
                        continue;
                    } else if (originalId.compareTo(secondId) > 0) {

                        //主库id大于二级库id，说明二级库多数据，需要删除
                        //判断插入队列是否失败
                        boolean cont = false;
                        while (!cont) {
                            //插入队列失败则等待100ms，返回false。然后重试
                            cont = needDelete.offer(secondId, 100, TimeUnit.MILLISECONDS);
                        }
                        //继续取二级库
                        secondInfo = secondQueue.poll();
                        continue;
                    } else if (originalId.equals(secondId) && originalDatetime.compareTo(secondDatetime) > 0) {

                        //主库和二级库id相等，但是二级库的更新时间比主库的早，说明二级库这个id需要更新数据
                        //判断插入队列是否失败
                        boolean cont = false;
                        while (!cont) {
                            //插入队列失败则等待100ms，返回false。然后重试
                            cont = needUpdate.offer(originalId, 100, TimeUnit.MILLISECONDS);
                        }
                        originalInfo = originalQueue.poll();
                        secondInfo = secondQueue.poll();
                        continue;
                    } else {
                        originalInfo = originalQueue.poll();
                        secondInfo = secondQueue.poll();
                        continue;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //统计时间使用
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从stone库中读取数据
     *
     * @param originalFileName
     */
    public void readOriginal(String originalFileName) {
        File file = new File(originalFileName);
        try {
            //读取文件
            originalIn = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String original = "";
            while ((original = originalIn.readLine()) != null) {
                try {
                    //判断插入队列是否失败
                    boolean cont = false;
                    while (!cont) {
                        //插入队列失败则等待1000ms，返回false。然后重试
                        cont = originalQueue.offer(original, 1000, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //向队列传递一个结束标记位
            //判断插入队列是否失败
            boolean cont = false;
            while (!cont) {
                //插入队列失败则等待1000ms，返回false。然后重试
                cont = originalQueue.offer("end", 1000, TimeUnit.MILLISECONDS);
            }
            log.error("主库文件读取完毕");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                originalIn.close();
                originalIn = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //统计时间使用
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从二级库中读取数据
     *
     * @param secondFileName
     */
    public void readTwo(String secondFileName) {
        File file = new File(secondFileName);
        try {
            //读取二级库文件
            secondIn = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String original = "";
            while ((original = secondIn.readLine()) != null) {
                try {
                    //判断插入队列是否失败
                    boolean cont = false;
                    while (!cont) {
                        //插入队列失败则等待1000ms，返回false。然后重试
                        cont = secondQueue.offer(original, 1000, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //向队列传递一个结束标记位
            //判断插入队列是否失败
            boolean cont = false;
            while (!cont) {
                //插入队列失败则等待1000ms，返回false。然后重试
                cont = secondQueue.offer("end", 1000, TimeUnit.MILLISECONDS);
            }
            log.error("二级库文件读取完毕!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                secondIn.close();
                secondIn = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //统计时间使用
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从队列中取出信息写入到文件
     *
     * @param fileName
     * @return
     */
    public void writeFileFromQueue(String fileName, LinkedBlockingQueue<String> blockingQueue) {
        File file = new File(fileName);
        BufferedWriter out = null;
        try {
            //创建文件输出流
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            //当originalIn输入流在工作，或者original库队列信息不为空时，进行循环

            //插入队列不为空时
            String value = null;
            //直到从主库队列中取出数据，才进行下一步
            while (true) {
                while (value == null) {
                    value = blockingQueue.poll();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if ("end".equals(value)) {
                    log.error(fileName + "写入完成！");
                    break;
                }
                out.write(value);
                out.newLine();
                out.flush();
                value = blockingQueue.poll();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //统计时间使用
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    /**
     * 进行stone库数据信息和二级库数据信息的比对
     *
     * @param originalFileName stone库文件路径名
     * @param secondFileName   二级库文件路径名
     */
    public String service(String originalFileName, String secondFileName) {

        if (!originalFileName.endsWith("_sorted.txt") && !secondFileName.endsWith("_sorted.txt")) {
            return "请传入文件名后缀为_sorted的TXT文件";
        } else {

            long startTime = System.currentTimeMillis();

            //输出文件目录
            int index = originalFileName.lastIndexOf("/");
            int fileNameIndex = originalFileName.lastIndexOf("_sorted.txt");
            String filePath = originalFileName.substring(0, index + 2) + originalFileName.substring(index + 2, fileNameIndex);

            //保存 需要插入二级库的ID集合 的文件
            String insertFileName = filePath + "_insert.txt";

            //保存 需要插入二级库的ID集合 的文件
            String updateFileName = filePath + "_update.txt";

            //保存 需要插入二级库的ID集合 的文件
            String deleteFileName = filePath + "_delete.txt";


            // 读主库文件的线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readOriginal(originalFileName);
                }
            }, "readOriginalFile-thread").start();

            // 读二级库文件的线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readTwo(secondFileName);
                }
            }, "readSecondFile-thread").start();

            //线程睡眠100毫秒
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //比对主库和二级库的线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    compare();
                }
            }, "compareTwoFileInfo").start();


            //将需要插入的id集合写入到插入文件中
            new Thread(new Runnable() {
                @Override
                public void run() {
                    writeFileFromQueue(insertFileName, needInsert);
                }
            }, "writeInsertFileFromQueue").start();

            //将需要更新的id集合写入到更新文件中
            new Thread(new Runnable() {
                @Override
                public void run() {
                    writeFileFromQueue(updateFileName, needUpdate);
                }
            }, "writeUpdateFileFromQueue").start();

            //将需要删除的id集合写入到删除文件中
            new Thread(new Runnable() {
                @Override
                public void run() {
                    writeFileFromQueue(deleteFileName, needDelete);
                }
            }, "writeDeleteFileFromQueue").start();

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            log.error("共用时：" + (endTime - startTime) + "ms");
            return "compare success!";
        }
    }

}
