/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effectutil;

import engine.Camera;
import engine.Light;
import java.util.List;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Kieran
 */
public class LightShader extends ShaderProgram {
    
    public LightShader(String vertFile, String fragFile) throws SlickException{
        super(readFile(vertFile), readFile(fragFile));
    }
    
    public void setUniformLight(String name, Light light){
                //setUniform2f(name + ".pos", light.screenX(), light.screenY());
                setUniform4f(name + ".color", light.tint);
                setUniform3f(name + ".attenuation", light.attenuation[0], light.attenuation[1], light.attenuation[2]);
                setUniform1f(name + ".intensity", light.intensity);
                //setUniform1f(name + ".scale", light.scale);
    }
    
    public void setUniformLightArray(String name, List<Light> lights){
            for(Light light: lights){
                setUniform2f(name + "[" + lights.indexOf(light) + "].pos", light.screenX(), light.screenY());
                setUniform4f(name + "[" + lights.indexOf(light) + "].color", light.tint);
                setUniform3f(name + "[" + lights.indexOf(light) + "].attenuation", light.attenuation[0], light.attenuation[1], light.attenuation[2]);
                setUniform1f(name + "[" + lights.indexOf(light) + "].intensity", light.intensity);
                setUniform1f(name + "[" + lights.indexOf(light) + "].scale", light.scale);
            }
    }
    
}
