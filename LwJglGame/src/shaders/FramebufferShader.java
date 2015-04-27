package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class FramebufferShader extends AbstractShader{

	private final static String vs ="res/shaders/vsFramebuffer.glsl";
	private final static String fs ="res/shaders/fsFramebuffer.glsl";
	
	private int transformation;
	public FramebufferShader() {
		super(vs, fs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateUniformLocation() {
		// TODO Auto-generated method stub
		transformation = super.getUniformLocation("model");
	}

	public void loadTranformationMatrix(Matrix4f matrix)
	{
		super.useProgram();
		super.uploadMat4f(transformation, matrix);
		
	}
	
	@Override
	protected void attributes() {
		// TODO Auto-generated method stub
		super.bindAttribLocation(0, "position");
		
	}
	
	@Override
	protected String getShaderType() {
		// TODO Auto-generated method stub
		return "Framebuffer";
	}
	
	
}
