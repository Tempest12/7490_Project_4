public class Surface
{
	Color4f diffuse;
	Color4f ambient;
    Color4f specular;
    
    float phongExp;
    float kRefl;
    
    boolean shiny;

	public Surface()
	{
		this(0.0f, 0.0f, 0.0f,
			 0.0f, 0.0f, 0.0f);
	}

	public Surface(float red, float green, float blue, float ambientRed, float ambientGreen, float ambientBlue)
	{
		this.diffuse = new Color4f(red, green, blue);
		this.ambient = new Color4f(ambientRed, ambientGreen, ambientBlue);
		
		this.shiny = false;
	}

    public Surface(float red, float green, float blue, float ambientRed, float ambientGreen, float ambientBlue, float specularRed, float specularGreen, float specularBlue, float phongExp, float kRefl)
    {
        this.diffuse = new Color4f(red, green, blue);
        this.ambient = new Color4f(ambientRed, ambientGreen, ambientBlue);
        this.specular = new Color4f(specularRed, specularGreen, specularBlue);
        
        this.phongExp = phongExp;
        this.kRefl = kRefl;
    
        this.shiny = true;
    }

	public String toString()
	{
	    if(shiny)
	    {
	        return "Surface: \n" +
	                    "\tDiffuse: " + diffuse.toString() + 
					    "\tAmbient: " + ambient.toString() + 
					    "\tSpecula: " + specular.toString();
	    }
	    else
	    {
		    return "Surface: \n" +
					    "\tDiffuse: " + diffuse.toString() + 
					    "\tAmbient: " + ambient.toString();
		}
	}
}
