/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effectutil;

import engine.Light;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kieran
 */
public class LightShader extends ShaderProgram {
    
    public LightShader(String vertFile, String fragFile) throws SlickException{
        super(readFile(vertFile), readFile(fragFile));
    }
    
    public void setUniformLightArray(String name, Light[] lights){
        for(int i = 0; i < lights.length; i++){
                Light light = lights[i];
                setUniform2f(name + "[" + i + "].pos", light.screenX(), light.screenY());
                setUniform4f(name + "[" + i + "].color", light.tint);
                setUniform3f(name + "[" + i + "].attenuation", light.attenuation[0], light.attenuation[1], light.attenuation[2]);
                setUniform1f(name + "[" + i + "].intensity", light.intensity);
                setUniform1f(name + "[" + i + "].scale", light.scale);
                setUniform1i(name + "[" + i + "].visible", 1);
            }
    }
    
}
