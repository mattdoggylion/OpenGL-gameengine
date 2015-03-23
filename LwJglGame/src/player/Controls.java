package player;

import java.util.List;

import matrix.Matrix;
import misc.RayCasting;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import enemies.Enemy;
import overlays.Sniper;
import rendering.DisplayWindow;
import shaders.MeshShader;
import shaders.TerrainShader;

public class Controls {

	State state = State.PLAYING;
	private Player player;
	private Sniper sniper;
	
	private MeshShader meshShader;
	private TerrainShader terrainShader;
	private short shot=0;
	private long shotTime=System.currentTimeMillis();
	
	private List<Enemy> enemies;
	private RayCasting mousecast;
	private long time= System.currentTimeMillis();
	private float speed=(float) 50.0;
	
	private boolean F1=false,F1Int=false;
	private boolean F2=false,F2Int=false;
	
	public boolean getF1()
	{
		return F1;
	}
	public boolean getF2()
	{
		return F2;
	}
	public Controls()
	{
		
	}
	public Controls(Player player,Sniper sniper,List<Enemy> enemies, MeshShader meshShader, TerrainShader terrainShader)
	{
		this.player=player;
		this.sniper=sniper;
		this.enemies=enemies;
		this.meshShader=meshShader;
		this.terrainShader=terrainShader;
		mousecast=new RayCasting(player.getCamera(),Matrix.calcProjectionMatrix());
		/*try {
			Mouse.setNativeCursor(new Cursor(0, 0, 0, 0, 0, null, null));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	public void playing()
	{
		double deltaTime= (System.currentTimeMillis()-time)/1000.0;
		time=System.currentTimeMillis();
		Vector2f lookat=player.getLookat();
		Vector3f position = player.getPosition();
		rotateCamera(lookat);
		boolean runningSpeed=false;
		updateShoot();
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)&&Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			speed=2000;
			if(!meshShader.getPlayerRunning())
			{
				meshShader.setPlayerRunning(true);
				Matrix.uploadProjectionMatrix(meshShader, 120);
			}
			if(!terrainShader.getPlayerRunning())
			{
				terrainShader.setPlayerRunning(true);
				Matrix.uploadProjectionMatrix(terrainShader, 120);
			}
			
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			speed=200;
			if(meshShader.getPlayerRunning())
			{
				meshShader.setPlayerRunning(false);
				Matrix.uploadProjectionMatrix(meshShader, 70);
			}
			if(terrainShader.getPlayerRunning())
			{
				terrainShader.setPlayerRunning(false);
				Matrix.uploadProjectionMatrix(terrainShader, 70);
			}
			
			
		}
		else
		{
			speed=50;
			if(meshShader.getPlayerRunning())
			{
				meshShader.setPlayerRunning(false);
				Matrix.uploadProjectionMatrix(meshShader, 70);
			}
			if(terrainShader.getPlayerRunning())
			{
				terrainShader.setPlayerRunning(false);
				Matrix.uploadProjectionMatrix(terrainShader, 70);
			}
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			if(!F1Int)
			{
				F1Int=true;
				F1=!F1;
				System.out.println("F1");
			}
		}
		else
		{
			F1Int=false;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F2))
		{
			if(!F2Int)
			{
				F2Int=true;
				F2=!F2;
				System.out.println("F2");
			}
		}
		else
		{
			F2Int=false;
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.z-=Math.cos(Math.toRadians(lookat.y))*speed*deltaTime;
			position.x+=Math.sin(Math.toRadians(lookat.y))*speed*deltaTime;
			sniper.walk();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x+=Math.cos(Math.toRadians(lookat.y))*speed*deltaTime;
			position.z+=Math.sin(Math.toRadians(lookat.y))*speed*deltaTime;
			sniper.walk();
			
		}
	if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
		position.z+=Math.cos(Math.toRadians(lookat.y))*speed*deltaTime;
		position.x-=Math.sin(Math.toRadians(lookat.y))*speed*deltaTime;
		sniper.walk();
		}
	if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
		position.x-=Math.cos(Math.toRadians(lookat.y))*speed*deltaTime;
		position.z-=Math.sin(Math.toRadians(lookat.y))*speed*deltaTime;
		sniper.walk();
		}
	
	if(Keyboard.isKeyDown(Keyboard.KEY_R))
	{
		sniper.reload();
	}
	
	if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
	{
		player.jump();
	}
	
	if(Keyboard.isKeyDown(Keyboard.KEY_Z))
	{
		position.y-=speed*deltaTime;
	}
	if(Keyboard.isKeyDown(Keyboard.KEY_X))
	{
		position.y+=speed*deltaTime;
	}
	if(Mouse.isButtonDown(0))
	{
		if(shot==0){
		/*mousecast.update();
		Vector3f ray=mousecast.getRay();
		for(Enemy enemy:enemies)
		{
			enemy.bulletCollision(player.getPosition(), ray);
				
		}*/
		shotTime=System.currentTimeMillis();
		shot=1;
		sniper.shoot();
	}}
		
	}
	
	public void updateShoot()
	{
		if(shot==1)
		{	
			if((System.currentTimeMillis()-shotTime)>sniper.getFrameTime()*10)
			{
				
				mousecast.update();
				Vector3f ray=mousecast.getRay();
				for(Enemy enemy:enemies)
				{
					enemy.bulletCollision(player.getPosition(), ray);
						
				}
				System.out.println("shooting");
				shot=2;
			}
		}
		else if(shot==2)
		{
			if((System.currentTimeMillis()-shotTime)>sniper.getFrameTime()*40)
			{
				shot=0;
				
			}
		}
		
	}
	
	private void rotateCamera(Vector2f pitchyaw)
	{
		pitchyaw.x-=Mouse.getDY();
		pitchyaw.y+=Mouse.getDX();
		if(pitchyaw.x<-80)pitchyaw.x=-80;
		else if(pitchyaw.x>80)pitchyaw.x=80;
		if(pitchyaw.y<0)pitchyaw.y+=360;
		else if(pitchyaw.y>360)pitchyaw.y-=360;
	}
	
}
