#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {
	mat2 rotate = mat2(cos(time), sin(time), -sin(time), cos(time));
	vec2 uv = (gl_FragCoord.xy / resolution)  * 2. - 1.;
	uv *= rotate;
	vec3 vcolor = (length(uv - atan(uv.y * uv.x)) > 0.3035) ? vec3((uv.y>0.)) : vec3(1,0,0);
	gl_FragColor = vec4(vcolor, 1.0 );
}