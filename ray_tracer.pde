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
        interpreter("t01.cli");
        setScreen(scene.render());
        break;

    case '2':  
        scene = new Scene(screen_width, screen_height);
        interpreter("t02.cli");
        setScreen(scene.render());
        break;

    case '3':
        scene = new Scene(screen_width, screen_height);  
        interpreter("t03.cli"); 
        setScreen(scene.render());
        break;

    case '4':  
        scene = new Scene(screen_width, screen_height);
        interpreter("t04.cli");
        setScreen(scene.render());
        break;

    case '5':  
        scene = new Scene(screen_width, screen_height);
        interpreter("t05.cli"); 
        setScreen(scene.render());
        break;

    case '6':  
        scene = new Scene(screen_width, screen_height);
        interpreter("t06.cli"); 
        setScreen(scene.render());
        break;

    case '7': 
        scene = new Scene(screen_width, screen_height);
        interpreter("t07.cli"); 
        setScreen(scene.render());
        break;

    case '8':  
        scene = new Scene(screen_width, screen_height);
        interpreter("t08.cli"); 
        setScreen(scene.render());
        break;

    case '9': 
        scene = new Scene(screen_width, screen_height);
        interpreter("t09.cli"); 
        setScreen(scene.render());
        break;

    case '0':
        scene = new Scene(screen_width, screen_height);
        interpreter("t10.cli"); 
        setScreen(scene.render());
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

  Vector3f one   = new Vector3f();
  Vector3f two   = new Vector3f();
  Vector3f three = new Vector3f();

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
        scene.addFace3f(one, two, three);
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
