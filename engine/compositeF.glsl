#version 120

uniform sampler2D backBuffer;
uniform sampler2D lightBuffer;

uniform vec3 ambientColor = vec3(1.0, 1.0, 1.0);
uniform float ambientIntensity = 0.1;

void main(void) {
    vec3 result = ambientColor * ambientIntensity;
    result += vec3(texture2D(lightBuffer, (gl_TexCoord[0].st)));
    result *= vec3(texture2D(backBuffer, (gl_TexCoord[0].st)));
    gl_FragColor = vec4( result, 1.0 );
}