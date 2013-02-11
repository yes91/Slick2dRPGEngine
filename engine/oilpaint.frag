
varying vec4 v_color;
uniform sampler2D src_tex_unit0;
uniform int choice;
uniform vec2 mouse;
uniform vec3 lightColor = vec3(1.0, 0.5, 0.2);
uniform vec3 lightAttenuation = vec3(0.4, 3.0, 20.0);
uniform float lightIntesity = 0.5; //Percentage from 0(0.0f) to 100(1.0f)
uniform vec3 ambientColor = vec3(1.0, 1.0, 1.0);
uniform vec3 ambientIntensity = 0.7; //Percentage from 0(0.0f) to 100(1.0f)
uniform bool isLit = true;
const int MAX_LIGHTS = 10;
uniform vec3[MAX_LIGHTS] lights;
const vec2 resolution = vec2(1280.0, 1280.0);

vec4 grayscale(vec4 src_color){
    float intensity = (src_color.r + src_color.g + src_color.b)/3.0;
    return vec4(intensity, intensity, intensity, src_color.a);
}

vec4 night(vec4 src_color){
    return vec4(src_color.r/2.0, src_color.g/2.0, src_color.b*1.1, src_color.a);
}

vec4 lighting(vec4 src_color){

    vec3 deltaPos = vec3(0.0);
    float d = 0.0;
    float att = 0.0;
    vec3 result = ambientColor * ambientIntensity;
    for(int i = 0; i < MAX_LIGHTS; i++){
        if(lights[i].x > 0.0 && isLit){
            deltaPos = vec3( (lights[i].xy - gl_FragCoord.xy) / resolution.xy, lights[i].z );
            d = sqrt(dot(deltaPos, deltaPos));
            att = 1.0 / ( lightAttenuation.x + (lightAttenuation.y*d) + (lightAttenuation.z*d*d) );
            result += (lightColor.rgb * lightIntesity) * att;
        }
    }

    result *= src_color.rgb;
    return vec4(result, src_color.a);
}

void main(void){
  // get the location of the current pixel
  // in the input texture
  vec3 lightPos = vec3(mouse, 0.0);
  lights[0] = lightPos;
  vec2 tex_coord = gl_TexCoord[0].st;

  // read the color of the current pixel out of the
  // input texture
  vec4 src_color = texture2D(src_tex_unit0, tex_coord).rgba;
  
  // output:
  // set color of the fragment
  switch(choice){
    case 0: gl_FragColor = src_color; break;
    case 1: gl_FragColor = grayscale(src_color); break;
    case 2: gl_FragColor = night(src_color); break;
    case 3: gl_FragColor = lighting(src_color); break;
    case 4: gl_FragColor = lighting(night(src_color)); break;
    case 5: gl_FragColor = lighting(grayscale(src_color)); break;
  }
}