/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author BaSiL DaViD
 */
public class Sphere {
    float[][] qat = new float[91][2];//Tangential circle
    float[][][] sphere = new float[91][361][3]; //halfsphere
    float radius;
    Texture tex;

    public Sphere(float radius, Texture tex) {
        this.tex = tex;
        this.radius = radius;
        for (int i=0; i<91; i++){
            qat[i][0] = (float) (radius*Math.cos(Math.toRadians(i)));//rad
            qat[i][1] = (float) (radius*Math.sin(Math.toRadians(i)));//alt
        }
        for (int i=0; i<91; i++){
            for(int j=0; j<361; j++){
                sphere[i][j][0] = (float) (qat[i][0]*Math.cos(Math.toRadians(j)));//x
                sphere[i][j][1] = qat[i][1];//y
                sphere[i][j][2] = (float) (qat[i][0]*Math.sin(Math.toRadians(j)));//z
            }
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        
        for (int i=0; i<91; i++){
            qat[i][0] = (float) (radius*Math.cos(Math.toRadians(i)));//rad
            qat[i][1] = (float) (radius*Math.sin(Math.toRadians(i)));//alt
        }
        for (int i=0; i<91; i++){
            for(int j=0; j<361; j++){
                sphere[i][j][0] = (float) (qat[i][0]*Math.cos(Math.toRadians(j)));//x
                sphere[i][j][1] = qat[i][1];//y
                sphere[i][j][2] = (float) (qat[i][0]*Math.sin(Math.toRadians(j)));//z
            }
        }
    }

    public Texture getTex() {
        return tex;
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }
    
    public void draw(){
        GL11.glPushMatrix();
        if (tex != null){
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            tex.bind();
        }
        for (int i=0; i<90; i++){
            for(int j=0; j<360; j++){
//Coloring with gray scale
//                        if(j<180)
//                            glColor3f(j/360f, j/360f, j/360f);
//                        else
//                            glColor3f(1-(j/360f), 1-(j/360f), 1-(j/360f));
//                        glColor3f(1, 0, 0);
                glBegin(GL_QUADS);
                    glTexCoord2f(j/360.0f, (90-i+1)/180.0f);
                    glVertex3f(sphere[i][j][0], sphere[i][j][1], sphere[i][j][2]);
                    glTexCoord2f((j+1)/360.0f, (90-i+1)/180.0f);
                    glVertex3f(sphere[i][j+1][0], sphere[i][j+1][1], sphere[i][j+1][2]);
                    glTexCoord2f((j+1)/360.0f, (90-i)/180.0f);
                    glVertex3f(sphere[i+1][j+1][0], sphere[i+1][j+1][1], sphere[i+1][j+1][2]);
                    glTexCoord2f(j/360.0f, (90-i)/180.0f);
                    glVertex3f(sphere[i+1][j][0], sphere[i+1][j][1], sphere[i+1][j][2]);
                glEnd();
                glBegin(GL_QUADS);
                    glTexCoord2f(j/360.0f, 0.5f+(i/180.0f));
                    glVertex3f(sphere[i][j][0], -sphere[i][j][1], sphere[i][j][2]);
                    glTexCoord2f((j+1)/360.0f, 0.5f+(i/180.0f));
                    glVertex3f(sphere[i][j+1][0], -sphere[i][j+1][1], sphere[i][j+1][2]);
                    glTexCoord2f((j+1)/360.0f, 0.5f+((i+1)/180.0f));
                    glVertex3f(sphere[i+1][j+1][0], -sphere[i+1][j+1][1], sphere[i+1][j+1][2]);
                    glTexCoord2f(j/360.0f, 0.5f+((i+1)/180.0f));
                    glVertex3f(sphere[i+1][j][0], -sphere[i+1][j][1], sphere[i+1][j][2]);
                glEnd();
            }
        }
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}
