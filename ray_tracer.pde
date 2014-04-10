///////////////////////////////////////////////////////////////////////
//
//  Ray Tracing Shell
//
///////////////////////////////////////////////////////////////////////

int screen_width = 600;
int screen_height = 600;

// global matrix values
PMatrix3D global_mat;
float[] gmat = new float[16];  // global matrix values

Scene scene;
ArrayList<ImageTexture> textures = new ArrayList<ImageTexture>();


// Some initializations for the scene.

void setup() {
   scene = new Scene(screen_width, screen_height);
  
  size (screen_width, screen_height, P3D);  // use P3D environment so that matrix commands work properly
  noStroke();
  colorMode (RGB, 1.0);
  background (0, 0, 0);
  
  // grab the global matrix values (to use later when drawing pixels)
  PMatrix3D global_mat = (PMatrix3D) getMatrix();
  global_mat.get(gmat);  
  //printMatrix();
  //resetMatrix();    // you may want to reset the matrix here

  interpreter("rect_test.cli");
}

// Press key 1 to 9 and 0 to run different test cases.

void keyPressed()
{
	resetMatrix();

  switch(key) {
    case '1':
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t01.cli");
        renderScene();
        break;

    case '2':  
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t02.cli");
        renderScene();
        break;

    case '3':
        scene = new Scene(screen_width, screen_height);  
        textures = new ArrayList<ImageTexture>();
        interpreter("t03.cli"); 
        renderScene();
        break;

    case '4':  
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t04.cli");
        renderScene();
        break;

    case '5':  
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t05.cli"); 
        renderScene();
        break;

    case '6':  
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t06.cli"); 
        renderScene();
        break;

    case '7': 
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t07.cli"); 
        renderScene();
        break;

    case '8':  
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t08.cli"); 
        renderScene();
        break;

    case '9': 
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t09.cli"); 
        renderScene();
        break;

    case '0':
        scene = new Scene(screen_width, screen_height);
        textures = new ArrayList<ImageTexture>();
        interpreter("t10.cli"); 
        renderScene();
        break;

    case 'q':  
        exit(); 
        break;
  }
}

//  Parser core. It parses the CLI file and processes it based on each 
//  token. Only "color", "rect", and "write" tokens are implemented. 
//  You should start from here and add more functionalities for your
//  ray tracer.
//
//  Note: Function "splitToken()" is only available in processing 1.25 or higher.

void interpreter(String filename)
{
  int count = 1;
  int texCount = 1;

  Vector3f one   = new Vector3f();
  Vector3f two   = new Vector3f();
  Vector3f three = new Vector3f();
  
  float[] texCoordOne = new float[2];
  float[] texCoordTwo = new float[2];
  float[] texCoordThree = new float[2];

  boolean textured = false;

  String str[] = loadStrings(filename);
  if (str == null) println("Error! Failed to read the file.");
  for (int i=0; i<str.length; i++) {
    
    String[] token = splitTokens(str[i], " "); // Get a line and parse tokens.
    if (token.length == 0) continue; // Skip blank line.
    
    if (token[0].equals("fov")) 
    {
		println("setting FOV");
      scene.setFOV(Float.parseFloat(token[1]));
    }
    else if (token[0].equals("background"))
    {
        scene.setBackGround(Float.parseFloat(token[1]),
                            Float.parseFloat(token[2]),
                            Float.parseFloat(token[3]));  
    }
    else if (token[0].equals("point_light"))
    {
        scene.addPointLight(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]),
                            Float.parseFloat(token[4]), Float.parseFloat(token[5]), Float.parseFloat(token[6]));      
    }
    else if (token[0].equals("diffuse"))
    {
        scene.setSurface(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]),
                         Float.parseFloat(token[4]), Float.parseFloat(token[5]), Float.parseFloat(token[6]));
    }
    else if (token[0].equals("shiny"))
    {
        scene.setSurface(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]),
                         Float.parseFloat(token[4]), Float.parseFloat(token[5]), Float.parseFloat(token[6]),
                         Float.parseFloat(token[7]), Float.parseFloat(token[8]), Float.parseFloat(token[9]),
                         Float.parseFloat(token[10]), Float.parseFloat(token[11]));
    }    
    else if (token[0].equals("begin"))
    {
    }
    else if (token[0].equals("end"))
    {
        count = 1;
        texCount = 1;
        
        if(textured)
        {
            scene.addFace3f(one, two, three, texCoordOne, texCoordTwo, texCoordThree);
        }
        else
        {
            scene.addFace3f(one, two, three);
        }
    }
    else if (token[0].equals("vertex"))
    {
		PVector vector = new PVector(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]));
		PVector target = new PVector();
		PMatrix3D ctm = (PMatrix3D)getMatrix();
		
		ctm.mult(vector, target);
	
        switch(count)
        {
            case 1:
                one.set(target.x, target.y, target.z);
                break;

            case 2:
                two.set(target.x, target.y, target.z);
                break;

            case 3:
                three.set(target.x, target.y, target.z);
                break;
        }

        count++;
    }
    else if (token[0].equals("sphere")) 
    {
		PVector vector = new PVector(Float.parseFloat(token[2]), Float.parseFloat(token[3]), Float.parseFloat(token[4]));
		PVector target = new PVector();
		PMatrix3D ctm = (PMatrix3D)getMatrix();
		
		ctm.mult(vector, target);
		
        //scene.addSphere(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]), Float.parseFloat(token[4]));  
		scene.addSphere(Float.parseFloat(token[1]), target.x, target.y, target.z);
    }
    else if (token[0].equals("push"))
    {
        pushMatrix();
    }
    else if (token[0].equals("pop"))
    {
        popMatrix();
    }
    else if (token[0].equals("translate")) {
		translate(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]));
    }
    else if (token[0].equals("rotate")) {
		rotate(radians(Float.parseFloat(token[1])), Float.parseFloat(token[2]), Float.parseFloat(token[3]), Float.parseFloat(token[4]));
    }
    else if (token[0].equals("scale")) {
		scale(Float.parseFloat(token[1]), Float.parseFloat(token[2]), Float.parseFloat(token[3]));
    }
    else if (token[0].equals("read")) {  // reads input from another file
      interpreter (token[1]);
    }
    else if (token[0].equals("color")) {
      float r = float(token[1]);
      float g = float(token[2]);
      float b = float(token[3]);
      fill(r, g, b);
    }
    else if (token[0].equals("rect")) {
      float x0 = float(token[1]);
      float y0 = float(token[2]);
      float x1 = float(token[3]);
      float y1 = float(token[4]);
      rect(x0, screen_height-y1, x1-x0, y1-y0);
    }
    else if (token[0].equals("write")) {
      // save the current image to a .png file
      //save(token[1]);  
    }
    else if (token[0].equals("image_texture"))
    {
        //System.out.println("About to load a texture. Also list is: " + textures.size());
        textured = true;
        scene.setTexture(token[1]); 
        textures.add(new ImageTexture(token[1]));   
        System.out.println("Texture Added.");
    }
    else if (token[0].equals("texture_coord"))
    {
        switch(texCount)
        {
            case 1:
                texCoordOne = new float[2];
                texCoordOne[0] = Float.parseFloat(token[1]);
                texCoordOne[1] = Float.parseFloat(token[2]);
                break;
                
            case 2:
                texCoordTwo = new float[2];
                texCoordTwo[0] = Float.parseFloat(token[1]);
                texCoordTwo[1] = Float.parseFloat(token[2]);
                break;
                
            case 3:
                texCoordThree = new float[2];
                texCoordThree[0] = Float.parseFloat(token[1]);
                texCoordThree[1] = Float.parseFloat(token[2]);
                break;
        }
        
        texCount++;
    }
  }
}

//  Draw frames.  Should be left empty.
void draw() {
}

// when mouse is pressed, print the cursor location
void mousePressed() {
  //println ("mouse: " + mouseX + " " + mouseY);
}

void setScreen(Color4f[][] screen)
{
    loadPixels();

    int index = 0;

    for(int x = 0; x < screen_width; x++)
    {
        for(int y = 0; y <screen_height; y++)
        {
           set(x, y, color(screen[x][y].red, screen[x][y].green, screen[x][y].blue));
        }
    }

    updatePixels();
}

void renderScene()
{
    Ray3f primaryRay = new Ray3f();
	Intersectable object;
	Collision collision = null;

	Color4f[][] frameBuffer = new Color4f[scene.width][scene.height];

	//Shoot Primary rays:
	for(int x = 0; x < scene.width; x++)
	{
		for(int y = 0; y < scene.height; y++)
		{
			scene.eyeRay(x, y, primaryRay);

			collision = scene.checkCollisions(primaryRay, null);

			if(collision == null)
			{
				frameBuffer[x][y] = scene.backgroundColor;
			}
			else 
			{
				frameBuffer[x][y] = shadePixel(collision.object, primaryRay, collision.time);
			}
			
			if(x == scene.halfWidth && y == scene.halfHeight)
			{
				System.out.println(collision);
			}
		}
	}

    setScreen(frameBuffer);
}

Color4f shadePixel(Intersectable object, Ray3f ray, float time)
{
    Collision collision = null;
	Collision reflectionCollision = null;
	Ray3f shadowRay = null;
	Ray3f reflectedRay = new Ray3f();
	Color4f pixelColor = new Color4f(object.surface.ambient);
	Color4f addedLight = null;
	Color4f reflectionColor = null;
	Color4f texel = new Color4f();
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
	
	for(int index = 0; index < scene.lights.size(); index++)
	{
		light = scene.lights.get(index);
		
		directionToLight.copy(light.position);
		directionToLight.subtract(intersectionPoint);
		directionToLight.normalize();
		
		shadowRay.direction.copy(directionToLight);
		
		collision = scene.checkCollisions(shadowRay, object);
		
		if(collision == null)
		{
			//Object can see the light:
			normal = object.findNormal(ray, time);
			
			partial = Math.abs(normal.dotProduct(directionToLight));
			
			if(object.textured)
			{
			    float[] uv = object.findUV(intersectionPoint);
			    textureLookUp(object, uv[0], uv[1], 0.0f, texel);
			    addedLight = Color4f.multiplyColors(light.diffuseColor, texel);			    
			}
			else
			{
			    addedLight = Color4f.multiplyColors(light.diffuseColor, object.surface.diffuse);
			}
			addedLight.scale(partial);
			
			pixelColor.add(addedLight);
			
			if(object.surface.shiny)
			{
			    inverseDirection.copy(ray.direction);
			    
			    inverseDirection.scale(-1.0f);
			    
			    specularRay.copy(directionToLight);
			    specularRay.add(inverseDirection);
			    specularRay.normalize();
			    
			    specularPartial = Math.abs(normal.dotProduct(specularRay));
			    specularPartial = (float)Math.pow(specularPartial, object.surface.phongExp);
			    
			    addedLight = Color4f.multiplyColors(object.surface.specular, light.diffuseColor);
			    addedLight.scale(specularPartial);
			    
			    pixelColor.add(addedLight);
			    
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
			        
			        reflectionCollision = scene.checkCollisions(reflectedRay, object);
			        
			        if(reflectionCollision != null)
			        {
			            reflectionColor = shadePixel(reflectionCollision.object, reflectedRay, reflectionCollision.time);
			            reflectionColor.scale(object.surface.kRefl);
			            
			            pixelColor.add(reflectionColor);
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
	
	return pixelColor;
}

void textureLookUp(Intersectable object, float u, float v, float w, Color4f output)
{
    for(int index = 0; index < textures.size(); index++)
    {
        if(textures.get(index).fileName.equals(object.textureName))
        {
            PVector texel = textures.get(index).color_value(new PVector(u, v, w));
            
            output.set(texel.x, texel.y, texel.z, 1.0f);
            
            return;
        }   
    }

    //System.out.println("Error! Texture: \"" + object.textureName + "\" not found.");
}
