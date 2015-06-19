package shaders;

import lighting.Light;

import org.lwjgl.util.vector.Matrix4f;

public class WaterShader extends AbstractShader {

	private int transformation;
	private int projectionMatrix;
	private int viewMatrix;
	private int lightPosition;
	private int lightColor;
	private int reflectivity;
	private int shine;
	
	private int reflectionTexture,refractionTexture;
	
	public WaterShader(String vs, String fs) {
		super(vs, fs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void attributes() {
		// TODO Auto-generated method stub
		super.bindAttribLocation(0, "position");
		super.bindAttribLocation(1, "texture");
		super.bindAttribLocation(2, "normal");
		
	}

	@Override
	protected void updateUniformLocation() {
		reflectionTexture=super.getUniformLocation("reflectionTexture");
		refractionTexture=super.getUniformLocation("refractionTexture");
		bindTexId();
		System.out.println("Reflection loc:" +reflectionTexture);
		
		transformation= super.getUniformLocation("model");
		projectionMatrix= super.getUniformLocation("projection");
		viewMatrix= super.getUniformLocation("view");
		lightPosition=super.getUniformLocation("lightPos");
		lightColor=super.getUniformLocation("lightCol");
		reflectivity=super.getUniformLocation("reflectivity");
		shine=super.getUniformLocation("shine");
		
	}
	
	public void bindTexId()
	{
		super.useProgram();
		super.uploadInt(reflectionTexture, 0);
		super.uploadInt(refractionTexture, 1);
	}
	public void loadTranformationMatrix(Matrix4f matrix)
	{
		super.uploadMat4f(transformation, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix)
	{
		
		super.uploadMat4f(projectionMatrix, matrix);
	}
	public void uploadLight(Light light){
		super.uploadVec3f(lightPosition, light.getPosition());
		super.uploadVec3f(lightColor, light.getColor());
	}
	public void uploadSpecular(float shine, float reflectivity)
	{
		super.uploadFloat(this.shine, shine);
		super.uploadFloat(this.reflectivity, reflectivity);
		
	}
	
	public void loadViewMatrix(Matrix4f matrix)
	{
		
		super.uploadMat4f(viewMatrix, matrix);
	}
	@Override
	protected String getShaderType() {
		// TODO Auto-generated method stub
		return "Water";
	}

	@Override
	public void updateTick() {
		// TODO Auto-generated method stub
		
	}

}
