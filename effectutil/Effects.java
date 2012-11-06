/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effectutil;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * @author redblast71
 */
public class Effects {
    public static final int NUM_KERNELS = 30;
    public static final float[][] GAUSSIAN_BLUR_KERNELS = generateGaussianBlurKernels(NUM_KERNELS);
    
    /** The size of the kernel used to blur the image */
	private static int blurKernelSize = 30;
	/** The number of passes applied to create the blur */
	private static int blurPasses = 1;
    
    public static void blur(BufferedImage image) {
		float[] matrix = GAUSSIAN_BLUR_KERNELS[blurKernelSize - 1];
		Kernel gaussianBlur1 = new Kernel(matrix.length, 1, matrix);
		Kernel gaussianBlur2 = new Kernel(1, matrix.length, matrix);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		ConvolveOp gaussianOp1 = new ConvolveOp(gaussianBlur1, ConvolveOp.EDGE_NO_OP, hints);
		ConvolveOp gaussianOp2 = new ConvolveOp(gaussianBlur2, ConvolveOp.EDGE_NO_OP, hints);
		BufferedImage scratchImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < blurPasses; i++) {
			gaussianOp1.filter(image, scratchImage);
			gaussianOp2.filter(scratchImage, image);
		}
	}
    
    public static float[][] generateGaussianBlurKernels(int level) {
		float[][] pascalsTriangle = generatePascalsTriangle(level);
		float[][] gaussianTriangle = new float[pascalsTriangle.length][];
		for (int i = 0; i < gaussianTriangle.length; i++) {
			float total = 0.0f;
			gaussianTriangle[i] = new float[pascalsTriangle[i].length];
			for (int j = 0; j < pascalsTriangle[i].length; j++) {
                        total += pascalsTriangle[i][j];
                    }
			float coefficient = 1 / total;
			for (int j = 0; j < pascalsTriangle[i].length; j++) {
                        gaussianTriangle[i][j] = coefficient * pascalsTriangle[i][j];
                    }
		}
		return gaussianTriangle;
	}
    
    private static float[][] generatePascalsTriangle(int level) {
		if (level < 2) {
                level = 2;
                }
		float[][] triangle = new float[level][];
		triangle[0] = new float[1];
		triangle[1] = new float[2];
		triangle[0][0] = 1.0f;
		triangle[1][0] = 1.0f;
		triangle[1][1] = 1.0f;
		for (int i = 2; i < level; i++) {
			triangle[i] = new float[i + 1];
			triangle[i][0] = 1.0f;
			triangle[i][i] = 1.0f;
			for (int j = 1; j < triangle[i].length - 1; j++) {
                        triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
                    }
		}
		return triangle;
	}
    
}
