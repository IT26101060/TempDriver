package lk.drivingschool.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public final class FileStorage {
    private static final Path DATA_DIR = Paths.get(
        System.getProperty("user.home"), ".drivingschool", "data");
    private static final Object LOCK = new Object();
    private FileStorage() {}

    private static Path path(String file) throws IOException {
        if (file == null || !file.matches("[A-Za-z0-9_-]+\\.txt"))
            throw new IllegalArgumentException("Invalid data filename");
        Files.createDirectories(DATA_DIR);
        return DATA_DIR.resolve(file);
    }
    private static String enc(String s) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
            (s == null ? "" : s).getBytes(StandardCharsets.UTF_8));
    }
    private static String dec(String s) {
        return new String(Base64.getUrlDecoder().decode(s), StandardCharsets.UTF_8);
    }
    public static List<List<String>> readRecords(String file) throws IOException {
        synchronized (LOCK) {
            Path p = path(file);
            List<List<String>> out = new ArrayList<>();
            if (!Files.exists(p)) return out;
            try (Stream<String> lines = Files.lines(p, StandardCharsets.UTF_8)) {
                lines.filter(x -> !x.isBlank()).forEach(line -> {
                    List<String> row = new ArrayList<>();
                    for (String part : line.split("\\|", -1)) row.add(dec(part));
                    out.add(row);
                });
            }
            return out;
        }
    }
    public static void appendRecord(String file, List<String> fields) throws IOException {
        synchronized (LOCK) {
            Files.writeString(path(file), encode(fields) + System.lineSeparator(),
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }
    public static void writeRecords(String file, List<List<String>> records) throws IOException {
        synchronized (LOCK) {
            Path p = path(file);
            Path tmp = Files.createTempFile(p.getParent(), "data-", ".tmp");
            try {
                List<String> lines = new ArrayList<>();
                for (List<String> row : records) lines.add(encode(row));
                Files.write(tmp, lines, StandardCharsets.UTF_8);
                try { Files.move(tmp, p, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE); }
                catch (AtomicMoveNotSupportedException e) {
                    Files.move(tmp, p, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally { Files.deleteIfExists(tmp); }
        }
    }
    private static String encode(List<String> row) {
        List<String> values = new ArrayList<>();
        for (String s : row) values.add(enc(s));
        return String.join("|", values);
    }
    public static boolean updateById(String file, String id, List<String> replacement) throws IOException {
        synchronized (LOCK) {
            List<List<String>> rows = readRecords(file);
            for (int i = 0; i < rows.size(); i++) {
                if (!rows.get(i).isEmpty() && rows.get(i).get(0).equals(id)) {
                    if (replacement.isEmpty() || !replacement.get(0).equals(id))
                        throw new IllegalArgumentException("Record ID cannot change");
                    rows.set(i, new ArrayList<>(replacement));
                    writeRecords(file, rows);
                    return true;
                }
            }
            return false;
        }
    }
    public static boolean deleteById(String file, String id) throws IOException {
        synchronized (LOCK) {
            List<List<String>> rows = readRecords(file);
            boolean removed = rows.removeIf(r -> !r.isEmpty() && r.get(0).equals(id));
            if (removed) writeRecords(file, rows);
            return removed;
        }
    }
}
