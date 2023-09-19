package com.sunset.discjockey.util.SpecialType;

public class MathMisc {
    public static double linearInterpolate(double _oVal, double _dVal, double r) {
        return _oVal + (_dVal - _oVal) * r;
    }

    public static double nonLinearInterpolate(double _oVal, double _dVal, double r, double n) {
        return linearInterpolate(_oVal, _dVal, 1 - java.lang.Math.pow(r, n));
    }

    public static class CubicSpline {
        private double[] x;
        private double[] y;
        private double[] h;
        private double[] b;
        private double[] u;
        private double[] v;
        private double[] z;

        public CubicSpline(double[] x, double[] y) {
            int n = x.length;
            this.x = x;
            this.y = y;
            h = new double[n];
            b = new double[n];
            u = new double[n];
            v = new double[n];
            z = new double[n];

            for (int i = 0; i < n - 1; i++) {
                h[i] = x[i + 1] - x[i];
                if (h[i] == 0) {
                    throw new IllegalArgumentException("Consecutive x-values are equal");
                }
                b[i] = (y[i + 1] - y[i]) / h[i];
            }

            u[1] = 2.0 * (h[0] + h[1]);
            v[1] = 6.0 * (b[1] - b[0]);

            for (int i = 2; i < n - 1; i++) {
                u[i] = 2.0 * (h[i - 1] + h[i]) - h[i - 1] * h[i - 1] / u[i - 1];
                v[i] = 6.0 * (b[i] - b[i - 1]) - h[i - 1] * v[i - 1] / u[i - 1];
            }

            z[n - 1] = 0.0;

            for (int i = n - 2; i > 0; i--) {
                z[i] = (v[i] - h[i] * z[i + 1]) / u[i];
            }
        }

        public double interpolate(double xValue) {
            int i = 0;
            int n = x.length;

            while (i < n - 1 && xValue > x[i + 1]) {
                i++;
            }

            double s = z[i] * (x[i + 1] - xValue) * (x[i + 1] - xValue) * (x[i + 1] - xValue) / (6.0 * h[i])
                    + z[i + 1] * (xValue - x[i]) * (xValue - x[i]) * (xValue - x[i]) / (6.0 * h[i])
                    + (y[i] - z[i] * h[i] * h[i] / 6.0) * (x[i + 1] - xValue) / h[i]
                    + (y[i + 1] - z[i + 1] * h[i] * h[i] / 6.0) * (xValue - x[i]) / h[i];

            return s;
        }
    }
}
