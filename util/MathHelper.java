/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Kieran
 */
public final class MathHelper {
    
    public static int clamp(int i, int min, int max){
        return Math.max(min, Math.min(max, i));
    }
    
    public static float clamp(float i, float min, float max){
        return Math.max(min, Math.min(max, i));
    }
    
    public static double clamp(double i, double min, double max){
        return Math.max(min, Math.min(max, i));
    }
    
    public static int scaleRange(int i, int min, int max, int newMin, int newMax) {
        return (((newMax-newMin)*(i - min))/(max - min)) + newMin;
    }
    
    public static float scaleRange(float i, float min, float max, float newMin, float newMax) {
        return (((newMax-newMin)*(i - min))/(max - min)) + newMin;
    }
    
    public static double scaleRange(double i, double min, double max, double newMin, double newMax) {
        return (((newMax-newMin)*(i - min))/(max - min)) + newMin;
    }
    
}
