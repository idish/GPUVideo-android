package com.daasuu.gpuv.egl.filter;


import android.opengl.GLES20;

public class GlSepiaFilter extends GlFilter {
    private static final String FRAGMENT_SHADER =
            "precision mediump float;" +
                    "varying vec2 vTextureCoord;" +
                    "uniform lowp sampler2D sTexture;" +
                    "\n" +
                    "uniform lowp mat4 colorMatrix;\n" +
                    "uniform lowp float intensity;\n" +
                    "const highp vec3 weight = vec3(0.2125, 0.7154, 0.0721);" +
                    "void main() {" +
                    "   lowp vec4 textureColor = texture2D(sTexture, vTextureCoord);\n" +
                    "   lowp vec4 outputColor = textureColor * colorMatrix;\n" +
                    "   \n" +
                     "   gl_FragColor = (intensity * outputColor) + ((1.0 - intensity) * textureColor);\n"+
                    "}";

    public GlSepiaFilter() {
        super(DEFAULT_VERTEX_SHADER, FRAGMENT_SHADER);
    }

    private final float[] colorMatrix =  new float[]{
        0.3588f, 0.7044f, 0.1368f, 0.0f,
                0.2990f, 0.5870f, 0.1140f, 0.0f,
                0.2392f, 0.4696f, 0.0912f, 0.0f,
                0f, 0f, 0f, 1.0f
    };

    private float intensity = 1.0f;

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    @Override
    public void onDraw() {
        GLES20.glUniformMatrix4fv(getHandle("colorMatrix"), 1, false, colorMatrix, 0);
        GLES20.glUniform1f(getHandle("intensity"), intensity);
    }

}
