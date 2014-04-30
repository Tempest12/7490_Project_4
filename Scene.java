import java.util.ArrayList;

public class Scene
{
	public ArrayList<Intersectable> objects;
	public ArrayList<PointLight>     lights;

	public float fov;

	public Color4f backgroundColor;

	public Surface lastSurface;

	public int width;
	public int height;

	public float halfWidth;
	public float halfHeight;

	public float k;

	public int idCounter;
	
	public String currentTexture;

	public Scene(int width, int height)
	{
		this.objects = new ArrayList<Intersectable>();
		this.lights = new ArrayList<PointLight>();

		this.fov = 0.0f;

		this.backgroundColor = new Color4f();

		this.lastSurface = new Surface();

		this.width  = width;
		this.height = height;

		this.halfWidth = width / 2.0f;
		this.halfHeight = height / 2.0f;

		this.idCounter = 0;
	}

	public void addFace3f(Vector3f one, Vector3f two, Vector3f three)
	{
		this.objects.add(new Face3f(idCounter, one, two, three, lastSurface));
		this.idCounter++;
	}
	
	public void addFace3f(Vector3f one, Vector3f two, Vector3f three, float[] texCoordOne, float[] texCoordTwo, float[] texCoordThree)
	{
	    this.objects.add(new Face3f(idCounter, one, two, three, lastSurface, currentTexture, texCoordOne, texCoordTwo, texCoordThree));
	    this.idCounter++;
	}

    public void addMarbleSphere(float radius, float x, float y, float z, float noiseScale)
    {
        Sphere sphere = new Sphere(this.idCounter, x, y, z, radius, lastSurface);
        sphere.setMarble();
        sphere.noiseScale = noiseScale;
        this.objects.add(sphere);
        this.idCounter++;
    }

    public void addNoiseSphere(float radius, float x, float y, float z, float noiseScale)
    {
        Sphere sphere = new Sphere(this.idCounter, x, y, z, radius, lastSurface);
        sphere.setNoise(noiseScale);
        this.objects.add(sphere);
        this.idCounter++;
    }

	public void addPointLight(float x, float y, float z, float red, float green, float blue)
	{
		this.lights.add(new PointLight(x, y, z, red, green, blue, 1.0f));
	}

	public void addSphere(float radius, float x, float y, float z)
	{
		this.objects.add(new Sphere(this.idCounter, x, y, z, radius, lastSurface));
		this.idCounter++;
	}

    public void addTexturedSphere(float radius, float x, float y, float z)
	{
		this.objects.add(new Sphere(this.idCounter, x, y, z, radius, lastSurface, currentTexture));
		this.idCounter++;
	}

    public void addWoodSphere(float radius, float x, float y, float z)
    {
        Sphere sphere = new Sphere(this.idCounter, x, y, z, radius, lastSurface);
        sphere.setWood();
        this.objects.add(sphere);
        this.idCounter++;
    }

	public Collision checkCollisions(Ray3f ray, Intersectable ignore)
	{
		Intersectable object = null;
		Intersectable firstHit = null;

		float minTime = Float.MAX_VALUE;
		float time = 0.0f;

		for(int index = 0; index < this.objects.size(); index++)
		{
			object = this.objects.get(index);

			if(ignore != null)
			{
				if(ignore.equals(object))
				{
					continue;
				}
			}

			time = object.intersectRay(ray);
		
			if(time > 0)
			{
				if(time < minTime)
				{
					firstHit = object;
					minTime = time;
				}
			}
		}

		if(firstHit == null)
		{
			return null;
		}
		else
		{
			return new Collision(firstHit, minTime);
		}
	}

	public void eyeRay(int x, int y, Ray3f ray)
	{
		ray.origin.x = 0.0f;
		ray.origin.y = 0.0f;
		ray.origin.z = 0.0f;

		ray.direction.x =  (x - this.halfWidth ) * (this.k / this.halfWidth );
		ray.direction.y = -(y - this.halfHeight) * (this.k / this.halfHeight);
		ray.direction.z = -1;

		ray.normalize();

          if(x == 34 && y == 34)
          {
             //System.out.println(ray.direction.toString());
	         
	         //System.out.println(k);
          }
	}

	public Color4f[][] render()
	{
		Ray3f primaryRay = new Ray3f();
		Intersectable object;
		Collision collision = null;

		Color4f[][] pixels = new Color4f[width][height];

		//Shoot Primary rays:
		for(int x = 0; x < this.width; x++)
		{
			for(int y = 0; y < this.height; y++)
			{
				this.eyeRay(x, y, primaryRay);

				collision = this.checkCollisions(primaryRay, null);

				if(collision == null)
				{
					pixels[x][y] = backgroundColor;
				}
				else 
				{
					pixels[x][y] = this.shade(collision.object, primaryRay, collision.time);
				}
				
				if(x == halfWidth && y == halfHeight)
				{
					System.out.println(collision);
				}
			}
		}

		return pixels;
	}

	public void setBackGround(float red, float green, float blue)
	{
		backgroundColor.set(red, green, blue);
	}

	public void setFOV(float fov)
	{
		this.fov = fov;
		
		System.out.println("Fov is:" + fov);
		
		this.k = (float)Math.tan(Math.toRadians(fov / 2.0f));
	}

	public void setSurface(float red, float green, float blue, float ambientRed, float ambientGreen, float ambientBlue)
	{
		lastSurface = new Surface(red, green, blue, ambientRed, ambientGreen, ambientBlue);
	}
	
	public void setSurface(float red, float green, float blue, float ambientRed, float ambientGreen, float ambientBlue, float specularRed, float specularGreen, float specularBlue, float phongExp, float kRefl)
	{
	    lastSurface = new Surface(red, green, blue, ambientRed, ambientGreen, ambientBlue, specularRed, specularGreen, specularBlue, phongExp, kRefl);
	}
	
	public Color4f shade(Intersectable object, Ray3f ray, float time)
	{
		Collision collision = null;
		Collision reflectionCollision = null;
		Ray3f shadowRay = null;
		Ray3f reflectedRay = new Ray3f();
		Color4f color = new Color4f(object.surface.ambient);
		Color4f addedLight = null;
		Color4f reflectionColor = null;
		PointLight light;

		float partial = 0.0f;
		float specularPartial = 0.0f;
		
		Vector3f reflectionAxis = new Vector3f();
		Vector3f intersectionPoint = ray.computeLocationAtTime(time);
		Vector3f directionToLight = new Vector3f();
		Vector3f directionFromLight = new Vector3f();
		Vector3f inverseDirection = new Vector3f();
		Vector3f specularRay = new Vector3f();
		Vector3f normal;
		
		shadowRay = new Ray3f(intersectionPoint);
		
		for(int index = 0; index < this.lights.size(); index++)
		{
			light = lights.get(index);
			
			directionToLight.copy(light.position);
			directionToLight.subtract(intersectionPoint);
			directionToLight.normalize();
			
			shadowRay.direction.copy(directionToLight);
			
			collision = this.checkCollisions(shadowRay, object);
			
			if(collision == null)
			{
				//Object can see the light:
				normal = object.findNormal(ray, time);
				
				partial = Math.max(0.0f, normal.dotProduct(directionToLight));
				
				addedLight = Color4f.multiplyColors(light.diffuseColor, object.surface.diffuse);
				addedLight.scale(partial);
				
				color.add(addedLight);
				
				if(object.surface.shiny)
				{
				    inverseDirection.copy(ray.direction);
				    
				    inverseDirection.scale(-1.0f);
				    
				    specularRay.copy(directionToLight);
				    specularRay.add(inverseDirection);
				    specularRay.normalize();
				    
				    specularPartial = Math.max(0, normal.dotProduct(specularRay));
				    specularPartial = (float)Math.pow(specularPartial, object.surface.phongExp);
				    
				    addedLight = Color4f.multiplyColors(object.surface.specular, light.diffuseColor);
				    addedLight.scale(specularPartial);
				    
				    color.add(addedLight);
				    
				    if(object.surface.kRefl > 0.0)
				    {
				        //Calculate the direction of the reflected ray.
				        reflectedRay.origin.copy(intersectionPoint);
				        
				        float reflectPartial = 2.0f * normal.dotProduct(inverseDirection);
				        reflectionAxis.copy(normal);
				        reflectionAxis.scale(reflectPartial);
				        reflectionAxis.add(ray.direction);
				        reflectionAxis.normalize();
				        
				        reflectedRay.direction.copy(reflectionAxis);
				        
				        reflectionCollision = checkCollisions(reflectedRay, object);
				        
				        if(reflectionCollision != null)
				        {
				            reflectionColor = this.shade(reflectionCollision.object, reflectedRay, reflectionCollision.time);
				            reflectionColor.scale(object.surface.kRefl);
				            
				            color.add(reflectionColor);
				        }
				        
				    }
				}				
			}
			else 
			{
				//Object is in Shadow.
				continue;
			}
		}
		
		return color;
	}

    public void setTexture(String textureName)
    {
        this.currentTexture = new String(textureName);
    }

	public String toString()
	{
		return "";
	}
	
	//Static stuff useful for debugging and not much else currently:
	public static Color4f color_white = new Color4f(1.0f);
}
