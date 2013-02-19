#version 120

uniform sampler2D src_tex_unit0;
uniform int choice;

struct Light
{
    vec2 pos;
    vec4 color;
    vec3 attenuation;
    float intensity;
    float scale;
};

//uniform vec4 lightColor = vec4(1.0, 0.5, 0.2, 1.0);
//uniform vec3 lightAttenuation = vec3(0.4, 3.0, 20.0); 
//uniform float lightIntesity = 0.5; //Percentage from 0(0.0f) to 100(1.0f)
uniform vec4 ambientColor = vec4(1.0, 1.0, 1.0, 1.0); //vec4(0.5, 0.5, 1.1, 1.0); Night color
uniform float ambientIntensity = 0.5; //Percentage from 0(0.0f) to 100(1.0f)
const int MAX_LIGHTS = 100;
//uniform vec3[MAX_LIGHTS] lights;
uniform Light[MAX_LIGHTS] lights;
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
    vec3 result = ambientColor.rgb * ambientIntensity;
    for(int i = 0; i < MAX_LIGHTS; i++){
            if(length(lights[i].pos) > 0){
                deltaPos = vec3( (lights[i].pos.xy - gl_FragCoord.xy) / resolution.xy, 0.0 );
                d = sqrt(dot(deltaPos, deltaPos)) * lights[i].scale;
                att = 1.0 / ( lights[i].attenuation.x + (lights[i].attenuation.y*d) + (lights[i].attenuation.z*d*d) );
                result += (lights[i].color.rgb * lights[i].intensity ) * att;
            }
    }

    result = result * src_color.rgb;
    //result = ((result - vec3(1.0, 1.0, 1.0)) + (src_color.rgb)); //I rather like the way 
    //this preserves the color of lights, but the ambient color has issues
    return vec4(result, src_color.a);
}

void main(void){
  // get the location of the current pixel
  // in the input texture
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
    case 4: gl_FragColor = lighting(src_color); break;
    case 5: gl_FragColor = lighting(grayscale(src_color)); break;
  }
}