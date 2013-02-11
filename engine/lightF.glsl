uniform sampler2D backbuffer;

void main() {
    vec2 tex_coord = gl_TexCoord[0].st;
    vec4 src_color = texture2D(backbuffer, tex_coord).rgba;
    gl_FragColor = src_color;
}
