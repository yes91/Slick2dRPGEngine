#version 120

uniform sampler2D backBuffer;
uniform sampler2D lightBuffer;

uniform vec3 ambientColor = vec3(1.0, 1.0, 1.0);
uniform float ambientIntensity = 0.1;

void main(void) {
    vec4 result;
    vec4 amb;
    vec2 tex_coord = gl_TexCoord[0].st;
    vec4 src_color = texture2D(backBuffer, tex_coord).rgba;
    vec4 dst_color = vec4(texture2D(lightBuffer, tex_coord).rgb, 1.0);
    amb = vec4(ambientColor * ambientIntensity, 1.0);
    
    result = (amb + (dst_color.r + dst_color.g + dst_color.b)/3.0 ) * (src_color );
    result += dst_color;
    gl_FragColor = result;
}