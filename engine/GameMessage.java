/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author redblast71
 */
public class GameMessage {
    
    public final int MAX_LINE = 4;
    
    public String[] texts;
    public String[][] pages;
    public String faceName;
    public int faceIndex;
    public int background;
    public int position;
    public Callback proc;
    
    
    public GameMessage(){
        clear();
    }
    
    public final void clear(){
        texts = new String[]{};
    }
    
    public boolean busy(){
        return texts.length > 0;
    }
    
    public void newPage(){
        /*while(texts.length % MAX_LINE > 0){
            texts.;
        }*/
    }
    
    public void setText(String in){
        in = in.replaceAll("\\\\C\\[([0-9]+)\\]", "\u0001[$1]");
        in = in.replaceAll(Pattern.quote("\\."), "\u0003");
        in = in.replaceAll(Pattern.quote("\\|"), "\u0004");
        //in = in.replaceAll(".{115}", "$0\n");
        String[] lines = in.split("\n");
        pages = transform(lines, 4);
    }
    
    public String[][] transform(String[] arr, int N) {
        int M = (arr.length + N - 1) / N;
        String[][] mat = new String[M][];
        int start = 0;
        for (int r = 0; r < M; r++) {
            int L = MAX_LINE;//Math.min(N, arr.length - start);
            mat[r] = java.util.Arrays.copyOfRange(arr, start, start + L);
            start += L;
        }
        for (String[] s : mat) {
            for (int i = 0; i < s.length; i++) {
                if (s[i] == null) {
                    s[i] = "";
                }
            }
        }
        return mat;
    }
}
