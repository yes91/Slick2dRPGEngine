// remember that you should draw a screen aligned quad
varying vec2 vTexCoord;
void main() {
   gl_TexCoord[0] = gl_MultiTexCoord0;
   vTexCoord = gl_MultiTexCoord0.xy;
   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}