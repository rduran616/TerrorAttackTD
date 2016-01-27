#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec4 v_position;

uniform sampler2D u_texture;

uniform mat4 u_projTrans;

uniform float coef0;
uniform float coef1;
uniform float coef2;
uniform float coef3;
uniform float ligthness;
uniform float brightness;
uniform float iGlobalTime;
uniform int pas;



vec4 rayon(vec4 couleur)
{
	vec4 color;

	//colonnes
	if((int)v_position.y%3==0)
		color = vec4(coef0,coef0,coef0-0.2,1);
	else if((int)v_position.y%3==1)
		color =  vec4(coef1,coef1,coef1-0.2,1);
	else if((int)v_position.y%3==2)
		color =  vec4(coef3,coef3,coef3-0.2,1);
		
		return color;
}

float snoise(vec2 co)
{
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main()
{
    vec4 color = texture2D(u_texture, v_texCoords);
    vec4 color2 = rayon(color);
    
    float n = snoise(vec2(v_position.x*cos(iGlobalTime),v_position.y*sin(iGlobalTime))); 
	vec4 fragColor = vec4(0.5+n*coef2/100, 0.5+n*coef2/100, 0.5+n*coef2/100, 1.0f);
	

    vec4 color3 = color2 * color  * fragColor;
    
    color3.rgb += (ligthness / 255);
	color3 = color3 - brightness * (color3 - 1.0) * color3 *(color3 - 0.5);
    
    color3.a = color.a;
    
    if(color3.r > 1)
    	color3.r=1;
	if(color3.g > 1)
		color3.g=1;
	if(color3.b > 1)
		color3.b=1;
    
    
	gl_FragColor = color3;
}