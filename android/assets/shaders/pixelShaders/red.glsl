#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() 
{
        vec3 color = texture2D(u_texture, v_texCoords).rgb;
        float gray = (color.r + color.g + color.b) / 3.0;
        vec3 greenscale = vec3(gray,0,0);
        
        if(gray <= 0.0 )
        {
        	 gl_FragColor = vec4(greenscale, 0.0);
        }
        else
        {        
        	 gl_FragColor = vec4(greenscale, 1.0);
        }
}