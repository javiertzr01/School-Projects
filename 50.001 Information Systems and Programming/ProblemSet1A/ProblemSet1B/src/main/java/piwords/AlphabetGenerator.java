package piwords;

import java.util.HashMap;

public class AlphabetGenerator {
    /**
     * Given a numeric base, return a char[] that maps every digit that is
     * representable in that base to a lower-case char.
     * 
     * This method will try to weight each character of the alphabet
     * proportional to their occurrence in words in a training set.
     * 
     * This method should do the following to generate an alphabet:
     *   1. Count the occurrence of each character a-z in trainingData.
     *   2. Compute the probability of each character a-z by taking
     *      (occurrence / total_num_characters).
     *   3. The output generated in step (2) is a PDF of the characters in the
     *      training set. Convert this PDF into a CDF for each character.
     *   4. Multiply the CDF value of each character by the base we are
     *      converting into.
     *   5. For each index 0 <= i < base,
     *      output[i] = (the first character whose CDF * base is > i)
     * 
     * A concrete example:
     * 	 0. Input = {"aaaaa..." (302 "a"s), "bbbbb..." (500 "b"s),
     *               "ccccc..." (198 "c"s)}, base = 93
     *   1. Count(a) = 302, Count(b) = 500, Count(c) = 198
     *   2. Pr(a) = 302 / 1000 = .302, Pr(b) = 500 / 1000 = .5,
     *      Pr(c) = 198 / 1000 = .198
     *   3. CDF(a) = .302, CDF(b) = .802, CDF(c) = 1
     *   4. CDF(a) * base = 28.086, CDF(b) * base = 74.586, CDF(c) * base = 93
     *   5. Output = {"a", "a", ... (29 As, indexes 0-28),
     *                "b", "b", ... (46 Bs, indexes 29-74),
     *                "c", "c", ... (18 Cs, indexes 75-92)}
     * 
     * The letters should occur in lexicographically ascending order in the
     * returned array.
     *   - {"a", "b", "c", "c", "d"} is a valid output.
     *   - {"b", "c", "c", "d", "a"} is not.
     *   
     * If base >= 0, the returned array should have length equal to the size of
     * the base.
     * 
     * If base < 0, return null.
     * 
     * If a String of trainingData has any characters outside the range a-z,
     * ignore those characters and continue.
     * 
     * @param base A numeric base to get an alphabet for.
     * @param trainingData The training data from which to generate frequency
     *                     counts. This array is not mutated.
     * @return A char[] that maps every digit of the base to a char that the
     *         digit should be translated into.
     */
    public static char[] BASIC_ALPHABET =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static char[] generateFrequencyAlphabet(int base,
                                                   String[] trainingData) {
        // TODO: Implement (Problem f)
        if (base < 0){
            return null;
        }
        char[] output = new char[base];
        HashMap<Character, Integer> alphaCount = new HashMap<Character, Integer>();
        HashMap<Character, Double> prob = new HashMap<Character, Double>();
        HashMap<Character, Double> cdf = new HashMap<Character, Double>();
        String input = String.join("", trainingData);
        int size = input.length();
        int total_num_characters = 0;
        for(int i = 0; i< size; i++){
            char alphabet = input.charAt(i);
            if(new String(BASIC_ALPHABET).indexOf(Character.toString(alphabet)) == -1){
                continue;
            }
            if (alphaCount.containsKey(alphabet)){
                total_num_characters += 1;
                int cur_count = alphaCount.get(alphabet);
                cur_count += 1;
                alphaCount.put(alphabet,cur_count);
            }
            else{
                alphaCount.put(alphabet, 1);
                total_num_characters += 1;
            }
        }
        for(char i: alphaCount.keySet()){
            double pr = (double) alphaCount.get(i) / total_num_characters;
            prob.put(i, pr);
        }
        double cur_cdf = 0;
        for(char i: prob.keySet()){
            cur_cdf += prob.get(i);
            double cur_input = cur_cdf*base*Math.pow(10,3);
            cur_input = Math.floor(cur_input);
            cur_input = cur_input / Math.pow(10, 3);
            cdf.put(i, cur_input);
        }
        int index = 0;
        for(int i=0; i < base; i++){
            for (char c: cdf.keySet()){
                if (cdf.get(c) > i){
                    output[i] = c;
                    break;
                }
            }
        }
        return output;
    }
}
