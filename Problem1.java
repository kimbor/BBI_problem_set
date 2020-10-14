/**
Problem 1: DNA sequence conversion

DNA is a long molecule that lies inside the nucleus of a cell, and it can be thought of as a
very long string consisting of characters in the alphabet {A, C, G, T}. DNA sequencing is the
technology that enables reading from DNA molecules and converting them to strings on the
output. We are interested in a technology that works in the following way: the DNA molecules
in the input are fragmented into pieces of equal length L; each piece is then sequenced by the
technology, and its content is encoded in the output. The particular encoding used in the output
is the following:

* The file contains multiple consecutive entries, one per piece.
* Each piece is represented by L consecutive bytes (1 byte = 8 bits).
* The first two (most significant) bits of each byte encode the DNA letter:
00 represents A
01 represents C
10 represents G
11 represents T
* The last six (least significant) bits of each byte encode the confidence that the readout
was correct, also known as the quality score. It is represented as an unsigned 6-bit
integer in the range 0 to 63.

Write a program that takes as input an encoded file as well as the number L, and converts it to a
text file of the following format (known as the FASTQ format):

* Each piece is represented by four lines:
- The first line contains the word @READ_ followed by the piece index. The first
piece has an index of 1, so its first line would be @READ_1
- The second line contains L characters in the {A,C,G,T} alphabet, representing
the DNA sequence of the piece.
- The third line contains the word +READ_ followed by the piece index (e.g.,
+READ_1).
- The fourth line contains L characters, representing the quality scores of the
piece. Each score is represented as an ASCII character in the range 33-96, by
adding 33 to the original score. For example, if the original score is 0, it should be
represented by the ASCII character 33 ("!")

Example input (for L=2) shown here in binary:
00000000 11100000 11000001 01111111

Example output:
@READ_1
AT
+READ_1
!A
@READ_2
TC
+READ2
"`
*/
import java.io.*;
import java.util.*;

public class Problem1 {
  static final String[] DNA_BASE_ENCODINGS = {"00", "01", "10", "11"};
  static final String[] DNA_BASES = {"A", "C", "G", "T"};

  public static void main(String[] args) {
    // test example reading from a String
    byte[] input = "00000000 11100000 11000001 01111111".getBytes();
    int L = 2;
    try (InputStream inputStream = new ByteArrayInputStream(input)) { // java 7 resource declaration syntax
      System.out.println(toFASTQ(inputStream, L));
    } catch (IOException io) {
        System.out.println("Error reading from string: " + io.getMessage());
    } // inputStream resource automagically closed

    // test example reading from a file
    String filename = "problem1.txt";
    try (InputStream inputStream = new FileInputStream(filename)) {  // java 7 resource declaration syntax
      System.out.println(toFASTQ(inputStream, 4));
    } catch (IOException io) {
        System.out.println("Error reading from file: " + io.getMessage());
    } // resources automagically closed
  }

  /**
  Read an encoded dna sequence from an input stream, and convert it to FASTQ format. Each dna sequence
  is assumed to be of length L.

  @param reader the stream to read from
  @param L the length of each dna fragment encoded in the stream
  */
  static String toFASTQ(InputStream inputStream, int L) throws IOException {
    StringBuffer output = new StringBuffer();
    int pieceIndex = 1; 

    String nextByte = getNextByte(inputStream);
    while (!nextByte.trim().equals("")) {
      output.append("@READ_" + pieceIndex + "\n");
      StringBuffer qualityOutput = new StringBuffer();;
      for (int i=0; i<L; i++) {
        String baseEncoded = nextByte.substring(0,2);
        String base = DNA_BASES[Arrays.binarySearch(DNA_BASE_ENCODINGS, baseEncoded)];
        output.append(base);
        int qualityScore = Byte.parseByte(nextByte.substring(2), 2);
        char qualityEncoded = (char) (qualityScore + 33);
        qualityOutput.append(qualityEncoded);
        nextByte = getNextByte(inputStream);
      }
      output.append("\n+READ_" + pieceIndex + "\n");
      output.append(qualityOutput + "\n");
      pieceIndex++;
    }
    return output.toString();
  }

  /**
  Fetch up to the next 8 binary digits from the stream. This function skips over any characters other than 0 or 1.
  If there are less than 8 binary digits remaining before the end of the stream, less than 8 are returned.
  If the inputStream is closed or no binary digits remain, an empty string is returned.

  @param inputStream the stream to read from
  @return a String with up to the next 8 binary digits in the stream
  */
  static String getNextByte(InputStream inputStream) throws IOException {
      StringBuffer byteBuffer = new StringBuffer();
      int nextBit = inputStream.read();

      while (nextBit != -1 && byteBuffer.length() < 8) { 
      // while we haven't reached the end of the stream and we haven't yet found 8 digits
        char nextBitChar = (char) nextBit;
        if (nextBitChar == '0' || nextBitChar == '1')
          byteBuffer.append((char) nextBit);
        // else do nothing - this skips over any whitespace or unexpected characters

        nextBit = inputStream.read();
      }
      return byteBuffer.toString();
  }
}