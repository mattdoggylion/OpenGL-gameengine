package _testing;


import lighting.Light;
import matrix.Camera;
import mesh.Mesh;
import mesh.MeshInstance;
import mesh.OBJLoader;
import mesh.TexMesh;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import rendering.DisplayWindow;
import rendering.LoadMesh;
import rendering.Render;
import shaders.MeshShader;
import textures.MeshTexture;

public class GameLoop {

	
	public static void main(String[] args) {
		long timeClock= System.nanoTime();
		DisplayWindow.create();
		LoadMesh loader= new LoadMesh();
		MeshShader meshShader = new MeshShader("src/shaders/vs.glsl","src/shaders/fs.glsl");
		Render renderer = new Render(meshShader);
		
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0,0,5),new Vector3f(1,1,1));
		
		
		MeshTexture tex= new MeshTexture(loader.loadTexture("sample_pic"));
tex.setShine(3);
tex.setReflectivity(2);
		
				OBJLoader objloader = new OBJLoader();
		Mesh m2= objloader.loadObj("monkey", loader);
		Mesh m3= objloader.loadObj("batman", loader);
		Mesh m4= objloader.loadObj("Avent", loader);
		Mesh m10= objloader.loadObj("sirus city", loader);
		TexMesh m2mesh = new TexMesh(m2,tex);
		TexMesh m3mesh = new TexMesh(m3,tex);
		TexMesh m4mesh = new TexMesh(m4,tex);
		TexMesh m10mesh = new TexMesh(m10,tex);
		MeshInstance mIns2 = new MeshInstance(m2mesh, new Vector3f(0,0,-2.5f),0,0,0,1);
		MeshInstance mIns3 = new MeshInstance(m3mesh, new Vector3f(2f,0,0),0,0,0,1);
		MeshInstance mIns4 = new MeshInstance(m4mesh, new Vector3f(-4f,3f,0),0,0,0,1);
		MeshInstance mIns5 = new MeshInstance(m4mesh, new Vector3f(-4f,0f,0),0,0,0,1);
		MeshInstance mIns6 = new MeshInstance(m4mesh, new Vector3f(-4f,-3f,0),0,0,0,1);
		MeshInstance mIns7 = new MeshInstance(m4mesh, new Vector3f(-4f,-6f,0),0,0,0,1);
		MeshInstance mIns10 = new MeshInstance(m10mesh, new Vector3f(0,-10,0),0,0,0,1f);
		meshShader.useProgram();
		float argument=0;
		while(!Display.isCloseRequested())
		{
			argument+=0.005;
			mIns2.rotate(0.0f, 1f, 0f);
			mIns4.rotate(0.0f, -1.5f, 0f);
			mIns5.rotate(0.0f, -1.7f, 0f);
			mIns6.rotate(0.0f, -1.9f, 0f);
			mIns7.rotate(0.0f, -2.1f, 0f);
			mIns3.setPosition(new Vector3f(2f,(float)((Math.sin(16*argument)-Math.sin(15*argument))*3),0f));
			light.setColor(new Vector3f(1f,1f-0.3f*(float)(Math.sin(argument)),1f-0.2f*(float)(Math.cos(argument))));
			camera.move();
			light.setPosition(camera.getPosition());
			camera.uploadViewMatrix(meshShader);
			renderer.prepareScene();
			meshShader.useProgram();
			meshShader.uploadLight(light);
			renderer.draw(mIns10,meshShader);
			renderer.draw(mIns2,meshShader);
			renderer.draw(mIns3,meshShader);
			renderer.draw(mIns4,meshShader);
			renderer.draw(mIns5,meshShader);
			renderer.draw(mIns6,meshShader);
			renderer.draw(mIns7,meshShader);
			
			meshShader.unbindShader();
			DisplayWindow.update();/*
			System.out.println(1000000000/(System.nanoTime()-timeClock));
			timeClock=System.nanoTime();*/
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				System.gc();
			}
		}
		meshShader.destroy();
		loader.destroy();
		DisplayWindow.destroy();
	}
	
}
