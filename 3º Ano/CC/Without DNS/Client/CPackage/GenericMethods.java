package CPackage;

import java.nio.ByteBuffer;

public class GenericMethods {
    
    public static String transformToFullIP(String ip) {
        // Split the IP address into its segments
        String[] segments = ip.split("\\.");

        // Create a StringBuilder to build the transformed IP
        StringBuilder fullIP = new StringBuilder();

        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];

            // Pad each segment with leading zeros to make it three digits
            while (segment.length() < 3) {
                segment = "0" + segment;
            }

            // Append the formatted segment to the full IP
            fullIP.append(segment);

            // Add a dot to separate segments, but not after the last one
            if (i < segments.length - 1) {
                fullIP.append(".");
            }
        }

        return fullIP.toString();
    }

    
    public static String padString(String originalString, int fixedSize) {
        // Ensure the string is not longer than the fixed size
        if (originalString.length() > fixedSize) {
            throw new IllegalArgumentException("String is too long");
        }

        // Pad the string to the right with spaces
        return String.format("%-" + fixedSize + "s", originalString);
    }

        public static byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return buffer.array();
    }
    
    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getLong();
    }
}
