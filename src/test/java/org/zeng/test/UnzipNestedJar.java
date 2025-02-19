package org.zeng.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述字符长度至少10个字符
 *
 * @author 曾祥江
 * @since 2025/2/8
 */
public class UnzipNestedJar {

    private static final Logger log = LoggerFactory.getLogger(UnzipNestedJar.class);

    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.out.println("Usage: java UnzipNestedJar <path_to_jar>");
//            return;
//        }
//        String pathname = args[0];
        String pathname = "D:\\doc\\jd\\cbe\\customs\\ASE_2023_zzzb\\Client\\bytec-ase-launch-client-0.0.2-SNAPSHOT.jar";

        File file = new File(pathname);
        if (!file.exists() || !file.isFile()) {
            log.error("Invalid JAR file path.");
            return;
        }
        String absolutePath = file.getAbsolutePath();
        String outPath = absolutePath.substring(0, absolutePath.indexOf("."));
        // 创建输出目录
        File outputDir = new File(outPath);

        try {
            unzipNestedJar(file);
        } catch (IOException e) {
            log.error("Error occurred during unzipping: {}", e.getMessage(), e);
        }
    }

    public static void unzipNestedJar(File jarFile) throws IOException {
        Deque<File> queue = new ArrayDeque<>();
        queue.add(jarFile);

        while (!queue.isEmpty()) {
            File currentJar = queue.poll();
            log.info("Processing JAR file: {}", currentJar.getAbsolutePath());
            String absolutePath = currentJar.getAbsolutePath();
            String outPath = absolutePath.substring(0, absolutePath.indexOf("."));
            // 创建输出目录
            File outputDir = new File(outPath);

            try (ZipFile zipFile = new ZipFile(currentJar)) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    Path entryPath = Paths.get(outputDir.toString(), entryName);
                    log.trace("EntryPath: {}", entryPath);

                    // Prevent path traversal attacks
                    if (!entryPath.normalize().startsWith(outputDir.toPath().normalize())) {
                        throw new IOException("Invalid entry path: " + entryName);
                    }

                    File entryFile = entryPath.toFile();

                    if (entry.isDirectory()) {
                        Files.createDirectories(entryFile.toPath());
                    } else {
                        Files.createDirectories(entryFile.getParentFile().toPath());
                        // 覆盖重复文件，不抛出异常
                        Files.deleteIfExists(entryFile.toPath());
                        Files.copy(zipFile.getInputStream(entry), entryFile.toPath());

                        if (entryName.endsWith(".jar") && entryName.startsWith("lib/bytec")) {
                            // If it's a nested JAR starting with "bytec", add it to the queue for processing
                            File nestedOutputDir = entryFile.getParentFile();
                            Files.createDirectories(nestedOutputDir.toPath());
                            queue.add(entryFile);
                            log.info("Found nested JAR: {}", entryFile.getAbsolutePath());
                            log.info("queue: {}", queue);
                            log.info("queue: {}", queue.size());
                        } else {
                            log.trace("Skipping non-nested-JAR file: {}", entryFile.getAbsolutePath());
                        }
                    }
                }
            } catch (IOException e) {
                log.error("Error processing JAR file: {} - {}", currentJar.getAbsolutePath(), e.getMessage(), e);
                throw new IOException("Failed to process JAR file: " + currentJar.getAbsolutePath(), e);
            }
        }
    }

}
