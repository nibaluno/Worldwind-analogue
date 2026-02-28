#version 410 core
out vec4 FragColor;

in vec2 TexCoords;
in vec3 Normal;
in vec3 FragPos;

uniform sampler2D tex;
uniform vec3 sunPos;

void main() {
    vec3 norm = normalize(Normal);

    float diff = max(dot(norm, vec3(0.0, 0.0, 1.0)), 0.7);

    vec3 color = texture(tex, TexCoords).rgb;
    FragColor = vec4(color * diff, 1.0);
}