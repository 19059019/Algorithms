import java.io.PrintStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.awt.geom.Line2D;
import java.io.IOException;

public class Main {
    public static double MAX = Math.pow(10, 13);

    public static void processCase(Reader in, StringBuilder out, int iteration) throws IOException {
        int k = in.nextInt();
        int p = in.nextInt();
        int q = in.nextInt();
        HashMap<Integer, Star> stars = new HashMap<Integer, Star>();
        HashSet<Line> beams = new HashSet<>();
        HashSet<Line> edges = new HashSet<>();
        Star startPoint = new Star(0, 0, 0, -1);
        Star target = new Star(k + 1, p, q, -1);
        double resultSum = 0;
        String result = "inf";

        // Index of start is 1
        stars.put(0, startPoint);
        // Index of target is k+1
        stars.put(k + 1, target);

        for (int i = 1; i <= k; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int theta = in.nextInt();
            Star newStar = new Star(i, x, y, theta);
            stars.put(i, newStar);
            beams.add(newStar.getBeam());
        }

        for (int i = 0; i < stars.size(); i++) {
            Star newStar = stars.get(i);
            for (int j = i; j < stars.size(); j++) {
                Star star = stars.get(j);
                if (i == j) {
                    continue;
                } else {
                    // Create edge
                    Line edge = new Line(newStar.getStart(), star.getStart());
                    // Check if the edge collides with any beam
                    boolean collision = false;
                    for (Line beam : beams) {
                        if (edge.intersects(beam)) {
                            Point intersection = edge.getIntersection(beam);
                            // Check if it intersects on the base
                            if (intersection == null) {
                                if (beam.isOnLine(newStar.getStart())) {
                                    if (newStar.getStart().getX() == beam.getStart().getX()
                                            && newStar.getStart().getY() == beam.getStart().getY()) {
                                    } else {
                                        collision = true;
                                        break;
                                    }
                                }
                                if (beam.isOnLine(star.getStart())) {
                                    if (star.getStart().getX() == beam.getStart().getX()
                                            && star.getStart().getY() == beam.getStart().getY()) {
                                    } else {
                                        collision = true;
                                        break;
                                    }
                                }
                                continue;
                            }
                            if (intersection.getX() == beam.getStart().getX()
                                    && intersection.getY() == beam.getStart().getY()) {
                                continue;
                            } else {
                                collision = true;
                                break;
                            }
                        }
                    }
                    if (!collision) {
                        if ((newStar == startPoint && star == target) || (star == startPoint && newStar == target)) {
                            resultSum = newStar.getStart().distTo(star.getStart());
                            result = String.format(Locale.US, "%.4f", (resultSum));
                            out.append("" + iteration + ": " + result + "\n");
                            return;
                        }
                        newStar.addOut(edge, j, star);
                        star.addOut(edge, i, newStar);
                        edges.add(edge);
                    }
                }
            }
        }
        resultSum = djikstra(stars, stars.get(0), target);
        if (resultSum >= 0) {
            result = String.format(Locale.US, "%.4f", (resultSum));
        } else {
            result = "inf";
        }
        out.append("" + iteration + ": " + result + "\n");
    }

    public static double djikstra(HashMap<Integer, Star> stars, Star source, Star target) {
        source.setDistance(0);
        int smallestIndex = -1;
        HashSet<Integer> visited = new HashSet<>();
        // While not everything has been visited
        while (visited.size() < stars.size()) {
            for (int i : source.getIndices()) {
                if (visited.contains(i)) {
                    continue;
                }
                // If the current set distance is greater than the new distance, then replace it
                double oldDist = source.getOutNodes().get(i).getDistance();
                double newDist = source.getDistance() + source.getOut().get(i).getLength();
                if (oldDist > newDist) {
                    source.getOutNodes().get(i).setDistance(newDist);
                    oldDist = newDist;
                }
                if (smallestIndex == -1) {
                    smallestIndex = i;
                } else if (newDist < stars.get(smallestIndex).getDistance()) {
                    smallestIndex = i;
                }
            }
            visited.add(source.getIndex());
            if (source.equals(target)) {
                return source.getDistance();
            }
            source = stars.get(smallestIndex);
            if (source == null) {
                break;
            }
            smallestIndex = -1;
        }
        return -1;
    }

    public static int getSmallesIndex(HashMap<Integer, Star> stars, HashSet<Integer> out, HashSet<Integer> visited) {
        int outPut = -1;
        double max = MAX;
        for (int i : out) {
            if (visited.contains(i)) {
                continue;
            } else {
                if (stars.get(i).getDistance() < max) {
                    outPut = i;
                    max = stars.get(i).getDistance();
                }
            }
        }

        return outPut;
    }

    public static void process(Reader in, StringBuilder out) throws IOException {
        int N = in.nextInt();
        for (int i = 1; i <= N; i++) {
            processCase(in, out, i);
        }
    }

    public static double dist() {
        return 0;
    }

    public static void main(String[] argv) throws IOException {
        StringBuilder s = new StringBuilder();
        process(new Reader(), s);
        System.out.print(s.toString());
    }
}

class Star {
    public static double MAX = Math.pow(10, 13);
    public static double MAX_RAD = Math.pow(10, 13);
    public static double MIN = -Math.pow(10, 13);
    private HashMap<Integer, Line> out = new HashMap<Integer, Line>();
    private HashMap<Integer, Star> outNodes = new HashMap<Integer, Star>();
    private HashSet<Integer> indices = new HashSet<>();
    private HashSet<Star> shortestPath = new HashSet<Star>();
    private double distance;
    private Line beam;
    private Point start;
    private Point end;
    private int theta;
    private int index;

    public Star(int i, int x, int y, int theta) {
        this.index = i;
        this.start = new Point(x, y);
        this.theta = theta;
        if (theta != -1) {
            this.end = findEnd(start, theta);
            distance = Double.MAX_VALUE;
        } else {
            if (x == 0 && y == 0) {
                distance = 0;
            } else {
                distance = Double.MAX_VALUE;
            }
            this.end = start;
        }
        beam = new Line(start, end);
        beam.setTheta(theta);
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the indices
     */
    public HashSet<Integer> getIndices() {
        return indices;
    }

    /**
     * @param indices the indices to set
     */
    public void setIndices(HashSet<Integer> indices) {
        this.indices = indices;
    }

    /**
     * @return the outNodes
     */
    public HashMap<Integer, Star> getOutNodes() {
        return outNodes;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the shortestPath
     */
    public HashSet<Star> getShortestPath() {
        return shortestPath;
    }

    /**
     * @param shortestPath the shortestPath to set
     */
    public void setShortestPath(HashSet<Star> shortestPath) {
        this.shortestPath = shortestPath;
    }

    /**
     * @return the out
     */
    public HashMap<Integer, Line> getOut() {
        return out;
    }

    /**
     * @param out the out to set
     */
    public void addOut(Line line, int index, Star star) {
        out.put(index, line);
        outNodes.put(index, star);
        indices.add(index);
    }

    /**
     * @return the beam
     */
    public Line getBeam() {
        return beam;
    }

    private Point findEnd(Point p, int theta) {
        double endX;
        double endY;
        double y = p.getY();
        double x = p.getX();
        double thetaRad = Math.toRadians(theta);

        // Special Cases
        if (theta == 0) {
            endX = MAX;
            endY = y;
        } else if (theta == 45) {
            endX = MAX + x;
            endY = MAX + y;
        } else if (theta == 90) {
            endX = x;
            endY = MAX;
        } else if (theta == 135) {
            endX = MIN + x;
            endY = MAX + y;
        } else if (theta == 180) {
            endX = MIN;
            endY = y;
        } else if (theta == 225) {
            endX = MIN + x;
            endY = MIN + y;
        } else if (theta == 270) {
            endX = x;
            endY = MIN;
        } else if (theta == 315) {
            endX = MAX + x;
            endY = MIN + y;
        } else {
            // General case
            endX = MAX_RAD * Math.cos(thetaRad) + x;
            endY = MAX_RAD * Math.sin(thetaRad) + y;
        }
        return new Point(endX, endY);
    }

    /**
     * @return the angle of the beam
     */
    public int getTheta() {
        return this.theta;
    }

    /**
     * @return the start
     */
    public Point getStart() {
        return start;
    }

    @Override
    public String toString() {
        return start.toString() + "Theta: " + theta;
    }
}

class Point {
    public static double MAX_RAD = Math.pow(10, 12);
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return this points x coord
     */
    public double getX() {
        return x;
    }

    /**
     * @return this points y coord
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * 
     * @param p
     * @return distance from this point to point p
     */
    public double distTo(Point p) {
        double xsum = x - p.getX();
        double ysum = y - p.getY();
        return Math.sqrt(Math.pow(xsum, 2) + Math.pow(ysum, 2));
    }
}

class Line {
    private Point start, end;
    private double length;
    private int theta = -1;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.length = start.distTo(end);
    }

    /**
     * @return the theta
     */
    public int getTheta() {
        return theta;
    }

    /**
     * @param theta the theta to set
     */
    public void setTheta(int theta) {
        this.theta = theta;
    }

    /**
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * @return the end
     */
    public Point getEnd() {
        return end;
    }

    /**
     * @return the start
     */
    public Point getStart() {
        return start;
    }

    @Override
    public String toString() {
        return start.toString() + " -> " + end.toString();
    }

    public boolean isOnLine(Point p) {
        if (p.distTo(start) + p.distTo(end) == length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     */
    public boolean intersects(Line l) {
        return linesIntersect(start.getX(), start.getY(), end.getX(), end.getY(), l.getStart().getX(),
                l.getStart().getY(), l.getEnd().getX(), l.getEnd().getY());
    }

    public Point getIntersection(Line l) {
        return getLineLineIntersection(start.getX(), start.getY(), end.getX(), end.getY(), l.getStart().getX(),
                l.getStart().getY(), l.getEnd().getX(), l.getEnd().getY());
    }

    // this code was take directly from
    // http://www.java-gaming.org/topics/fastest-linesintersect-method/22590/view.html
    private boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
            double y4) {
        // Return false if either of the lines have zero length
        if (x1 == x2 && y1 == y2 || x3 == x4 && y3 == y4) {
            return false;
        }
        // Fastest method, based on Franklin Antonio's "Faster Line Segment
        // Intersection" topic "in Graphics Gems III" book
        // (http://www.graphicsgems.org/)
        double ax = x2 - x1;
        double ay = y2 - y1;
        double bx = x3 - x4;
        double by = y3 - y4;
        double cx = x1 - x3;
        double cy = y1 - y3;

        double alphaNumerator = by * cx - bx * cy;
        double commonDenominator = ay * bx - ax * by;
        if (commonDenominator > 0) {
            if (alphaNumerator < 0 || alphaNumerator > commonDenominator) {
                return false;
            }
        } else if (commonDenominator < 0) {
            if (alphaNumerator > 0 || alphaNumerator < commonDenominator) {
                return false;
            }
        }
        double betaNumerator = ax * cy - ay * cx;
        if (commonDenominator > 0) {
            if (betaNumerator < 0 || betaNumerator > commonDenominator) {
                return false;
            }
        } else if (commonDenominator < 0) {
            if (betaNumerator > 0 || betaNumerator < commonDenominator) {
                return false;
            }
        }
        if (commonDenominator == 0) {
            // This code wasn't in Franklin Antonio's method. It was added by Keith
            // Woodward.
            // The lines are parallel.
            // Check if they're collinear.
            double y3LessY1 = y3 - y1;
            double collinearityTestForP3 = x1 * (y2 - y3) + x2 * (y3LessY1) + x3 * (y1 - y2); // see
                                                                                              // http://mathworld.wolfram.com/Collinear.html
            // If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2
            // is parallel with p3-p4
            if (collinearityTestForP3 == 0) {
                // The lines are collinear. Now check if they overlap.
                if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 || x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4
                        || x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2) {
                    if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 || y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4
                            || y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    // this code was take directly from
    // http://www.java-gaming.org/topics/fastest-linesintersect-method/22590/view.html
    private Point getLineLineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
            double y4) {
        double det1And2 = det(x1, y1, x2, y2);
        double det3And4 = det(x3, y3, x4, y4);
        double x1LessX2 = x1 - x2;
        double y1LessY2 = y1 - y2;
        double x3LessX4 = x3 - x4;
        double y3LessY4 = y3 - y4;
        double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
        if (det1Less2And3Less4 == 0) {
            // the denominator is zero so the lines are parallel and there's either no
            // solution (or multiple solutions if the lines overlap) so return null.
            return null;
        }
        double x = (det(det1And2, x1LessX2, det3And4, x3LessX4) / det1Less2And3Less4);
        double y = (det(det1And2, y1LessY2, det3And4, y3LessY4) / det1Less2And3Less4);

        return new Point((double) Math.round(x * 100000d) / 100000d, (double) Math.round(y * 100000d) / 100000d);
    }

    // this code was take directly from
    // http://www.java-gaming.org/topics/fastest-linesintersect-method/22590/view.html
    private static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }
}