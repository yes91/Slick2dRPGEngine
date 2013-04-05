//uniform sampler2D backbuffer;

varying vec2 pos;

struct Light
{
    vec4 color;
    vec3 attenuation;
    float intensity;
};

uniform Light light;
//const vec2 resolution = vec2(1280.0, 1280.0);

void main() {
    vec3 deltaPos = vec3( pos.xy , 0.0 );
    float d = sqrt(dot(deltaPos, deltaPos)) / 2.0;
    float att = 1.0 / ( light.attenuation.x + (light.attenuation.y*d) + (light.attenuation.z*d*d) );
    //float att = 1.0 / sqrt(pos.x*pos.x + pos.y*pos.y);
    att = (att - 0.15) / (1.0 - 0.15);
    vec3 result = light.color.rgb  * att;
    gl_FragColor = vec4( result, light.intensity );
}
