package frc.robot.subsystems;

// Import packages needed to run
import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.opencv.core.*;
import org.opencv.imgproc.*;

public class GripPipeline implements VisionPipeline {

	// Initialize Outputs
	private Mat hsvThresholdOutput = new Mat();
	private Mat cvErodeOutput = new Mat();
	private Mat cvDilateOutput = new Mat();
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();
	
	public double hsvThresholdHueLow = 50.0;
	public double hsvThresholdHueHigh = 80.0;
	public double hsvThresholdSaturationLow = 10.0;
	public double hsvThresholdSaturationHigh = 255.0;
	public double hsvThresholdValueLow = 50.0;
	public double hsvThresholdValueHigh = 255.0;

	// Loads the core library from openCV
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	// This is the primary method that runs the entire pipeline and updates the outputs
	@Override	public void process(Mat source0) {

		hsvThresholdHueLow = SmartDashboard.getNumber("hsvThresholdHueLow", hsvThresholdHueLow);
 		hsvThresholdHueHigh = SmartDashboard.getNumber("hsvThresholdHueHigh", hsvThresholdHueHigh);
		hsvThresholdSaturationLow = SmartDashboard.getNumber("hsvThresholdSaturationLow", hsvThresholdSaturationLow);
		hsvThresholdSaturationHigh = SmartDashboard.getNumber("hsvThresholdSaturationHigh", hsvThresholdSaturationHigh);
		hsvThresholdValueLow = SmartDashboard.getNumber("hsvThresholdValueLow", hsvThresholdValueLow);
		hsvThresholdValueHigh = SmartDashboard.getNumber("hsvThresholdValueHigh", hsvThresholdValueHigh);
		// Step HSV_Threshold0:
		Mat hsvThresholdInput = source0;

		double[] hsvThresholdHue = {hsvThresholdHueLow, hsvThresholdHueHigh};
		double[] hsvThresholdSaturation = {hsvThresholdSaturationLow, hsvThresholdSaturationHigh};
		double[] hsvThresholdValue = {hsvThresholdValueLow, hsvThresholdValueHigh};
		SmartDashboard.putNumber("hsvThresholdHueLow", hsvThresholdHueLow);
		SmartDashboard.putNumber("hsvThresholdHueHigh", hsvThresholdHueHigh);
		SmartDashboard.putNumber("hsvThresholdSaturationLow", hsvThresholdSaturationLow);
		SmartDashboard.putNumber("hsvThresholdSaturationHigh", hsvThresholdSaturationHigh);
		SmartDashboard.putNumber("hsvThresholdValueLow", hsvThresholdValueLow);
		SmartDashboard.putNumber("hsvThresholdValueHigh", hsvThresholdValueHigh);
		hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);

		// Step CV_erode0:
		Mat cvErodeSrc = hsvThresholdOutput;
		Mat cvErodeKernel = new Mat();
		Point cvErodeAnchor = new Point(-1, -1);
		double cvErodeIterations = 4.0;
		int cvErodeBordertype = Core.BORDER_CONSTANT;
		Scalar cvErodeBordervalue = new Scalar(-1);
		cvErode(cvErodeSrc, cvErodeKernel, cvErodeAnchor, cvErodeIterations, cvErodeBordertype, cvErodeBordervalue, cvErodeOutput);

		// Step CV_dilate0:
		Mat cvDilateSrc = cvErodeOutput;
		Mat cvDilateKernel = new Mat();
		Point cvDilateAnchor = new Point(-1, -1);
		double cvDilateIterations = 4.0;
		int cvDilateBordertype = Core.BORDER_CONSTANT;
		Scalar cvDilateBordervalue = new Scalar(-1);
		cvDilate(cvDilateSrc, cvDilateKernel, cvDilateAnchor, cvDilateIterations, cvDilateBordertype, cvDilateBordervalue, cvDilateOutput);

		// Step Find_Contours0:
		Mat findContoursInput = cvDilateOutput;
		boolean findContoursExternalOnly = false;
		findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);

		// Step Filter_Contours0:
		ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
		double filterContoursMinArea = 0.0;
		double filterContoursMinPerimeter = 0.0;
		double filterContoursMinWidth = 0.0;
		double filterContoursMaxWidth = 1000.0;
		double filterContoursMinHeight = 0.0;
		double filterContoursMaxHeight = 1000.0;
		double[] filterContoursSolidity = {0, 100};
		double filterContoursMaxVertices = 31000.0;
		double filterContoursMinVertices = 0.0;
		double filterContoursMinRatio = 0.0;
		double filterContoursMaxRatio = 1000.0;
		filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter, filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight, filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio, filterContoursMaxRatio, filterContoursOutput);
	}
	// Returns a Mat of the hsvThresholdOutput
	public Mat hsvThresholdOutput() {
		return hsvThresholdOutput;
	}

	// Returns a Mat of the cvErodeOutput
	public Mat cvErodeOutput() {
		return cvErodeOutput;
	}

	// Returns a Mat of the cvDilateOutput
	public Mat cvDilateOutput() {
		return cvDilateOutput;
	}

	// Returns an ArrayList of Mat Points of the findContoursOutput
	public ArrayList<MatOfPoint> findContoursOutput() {
		return findContoursOutput;
	}

	// Retursn an ArrayList of Mat Points of the filterContoursOutput
	public ArrayList<MatOfPoint> filterContoursOutput() {
		return filterContoursOutput;
	}

	/** Segment an image based on hue, saturation, and value ranges.
	 *
	 * @param input The image on which to perform the HSL threshold.
	 * @param hue The min and max hue
	 * @param sat The min and max saturation
	 * @param val The min and max value
	 * @param output The image in which to store the output.
	 * */
	public void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
	    Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
		Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
			new Scalar(hue[1], sat[1], val[1]), out);
	}

	/** Expands area of lower value in an image.
	 * @param src the Image to erode.
	 * @param kernel the kernel for erosion.
	 * @param anchor the center of the kernel.
	 * @param iterations the number of times to perform the erosion.
	 * @param borderType pixel extrapolation method.
	 * @param borderValue value to be used for a constant border.
	 * @param dst Output Image.
	 * */
	public void cvErode(Mat src, Mat kernel, Point anchor, double iterations,
		int borderType, Scalar borderValue, Mat dst) {
		if (kernel == null) {
			kernel = new Mat();
		}
		if (anchor == null) {
			anchor = new Point(-1,-1);
		}
		if (borderValue == null) {
			borderValue = new Scalar(-1);
		}
		Imgproc.erode(src, dst, kernel, anchor, (int)iterations, borderType, borderValue);
	}

	/** Expands area of higher value in an image.
	 * @param src the Image to dilate.
	 * @param kernel the kernel for dilation.
	 * @param anchor the center of the kernel.
	 * @param iterations the number of times to perform the dilation.
	 * @param borderType pixel extrapolation method.
	 * @param borderValue value to be used for a constant border.
	 * @param dst Output Image.
	 */
	public void cvDilate(Mat src, Mat kernel, Point anchor, double iterations,
	int borderType, Scalar borderValue, Mat dst) {
		if (kernel == null) {
			kernel = new Mat();
		}
		if (anchor == null) {
			anchor = new Point(-1,-1);
		}
		if (borderValue == null){
			borderValue = new Scalar(-1);
		}
		Imgproc.dilate(src, dst, kernel, anchor, (int)iterations, borderType, borderValue);
	}

	/** Sets the values of pixels in a binary image to their distance to the nearest black pixel.
	 * @param input The image on which to perform the Distance Transform.
	 * @param type The Transform.
	 * @param maskSize the size of the mask.
	 * @param output The image in which to store the output.
	 */
	public void findContours(Mat input, boolean externalOnly,
		List<MatOfPoint> contours) {
		Mat hierarchy = new Mat();
		contours.clear();
		int mode;
		if (externalOnly) {
			mode = Imgproc.RETR_EXTERNAL;
		}
		else {
			mode = Imgproc.RETR_LIST;
		}
		int method = Imgproc.CHAIN_APPROX_SIMPLE;
		Imgproc.findContours(input, contours, hierarchy, mode, method);
	}

	/** Filters out contours that do not meet certain criteria.
	 * @param inputContours is the input list of contours
	 * @param output is the the output list of contours
	 * @param minArea is the minimum area of a contour that will be kept
	 * @param minPerimeter is the minimum perimeter of a contour that will be kept
	 * @param minWidth minimum width of a contour
	 * @param maxWidth maximum width
	 * @param minHeight minimum height
	 * @param maxHeight maximimum height
	 * @param Solidity the minimum and maximum solidity of a contour
	 * @param minVertexCount minimum vertex Count of the contours
	 * @param maxVertexCount maximum vertex Count
	 * @param minRatio minimum ratio of width to height
	 * @param maxRatio maximum ratio of width to height
	 */
	public void filterContours(List<MatOfPoint> inputContours, double minArea,
		double minPerimeter, double minWidth, double maxWidth, double minHeight, double
		maxHeight, double[] solidity, double maxVertexCount, double minVertexCount, double
		minRatio, double maxRatio, List<MatOfPoint> output) {
		final MatOfInt hull = new MatOfInt();
		output.clear();
		//operation
		for (int i = 0; i < inputContours.size(); i++) {
			final MatOfPoint contour = inputContours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);
			if (bb.width < minWidth || bb.width > maxWidth) continue;
			if (bb.height < minHeight || bb.height > maxHeight) continue;
			final double area = Imgproc.contourArea(contour);
			if (area < minArea) continue;
			if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) continue;
			Imgproc.convexHull(contour, hull);
			MatOfPoint mopHull = new MatOfPoint();
			mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
			for (int j = 0; j < hull.size().height; j++) {
				int index = (int)hull.get(j, 0)[0];
				double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1]};
				mopHull.put(j, 0, point);
			}
			final double solid = 100 * area / Imgproc.contourArea(mopHull);
			if (solid < solidity[0] || solid > solidity[1]) continue;
			if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount)	continue;
			final double ratio = bb.width / (double)bb.height;
			if (ratio < minRatio || ratio > maxRatio) continue;
			output.add(contour);
		}
	}
}