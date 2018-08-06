import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.datatransfer.*; 
import java.awt.dnd.*; 
import java.io.File; 
import java.io.IOException; 
import java.awt.Component; 
import java.util.List; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ieimaker extends PApplet {

  
  
  
  



DropTarget dropTarget;  
Component component;
PImage source;
PImage iei;
PImage export;
boolean exportflag;

float ieiwidth;
float ieiheight;
int ieix;
int ieiy;

int t=0;
;
public void dragDropFile() {
  component = (Component)this.surface.getNative();

  dropTarget = new DropTarget(component, new DropTargetListener() {  
    public void dragEnter(DropTargetDragEvent dtde) {
    }  
    public void dragOver(DropTargetDragEvent dtde) {
    }  
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }  
    public void dragExit(DropTargetEvent dte) {
    }  
    public void drop(DropTargetDropEvent dtde) {  
      dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);  
      Transferable trans = dtde.getTransferable();  
      List<File> fileNameList = null;  
      if (trans.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {  
        try {  
          fileNameList = (List<File>)  
            trans.getTransferData(DataFlavor.javaFileListFlavor);
        } 
        catch (UnsupportedFlavorException ex) {  
          println(ex);
        } 
        catch (IOException ex) {  
          println(ex);
        }
      }  
      if (fileNameList == null) return;  

      for (File f : fileNameList) {
        println(f.getAbsolutePath());
        source=loadImage(f.getAbsolutePath());
      }
    }
  }
  );
}

public void setup() {
  
  dragDropFile();
  iei=loadImage("iei.png");
  ieiwidth=iei.width;
  ieiheight=iei.height;
}
public void draw() {

  drawimages();
}

public void drawimages() {
  background(255);

  if (ieix-ieiwidth/2<0) {
    translate(-(int)(ieix-ieiwidth/2), 0);
  } else if (ieix+ieiwidth/2>width) {
    translate(width-(int)(ieix+ieiwidth/2), 0);
  }

  if (ieiy-ieiheight/2<0) {
    translate(0, -(int)(ieiy-ieiheight/2));
  } else if (ieiy+ieiheight/2>height) {
    translate(0, height-(int)(ieiy+ieiheight/2));
  }


  if (source!=null) {
    if (source.width>width||source.height>height) {
      image(source, width/2, height/2, map(source.width, 0, source.width, 0, width), map(source.height, 0, source.height, 0, height));
    } else {
      image(source, width/2, height/2);
    }
    filter(GRAY);
  }

  imageMode(CENTER);
  ieix=mouseX;
  ieiy=mouseY;
  image(iei, ieix, ieiy, ieiwidth, ieiheight);

  if (exportflag) {
    textAlign(CENTER);
    fill(0);
    textSize(48);
    text("Now Export...", ieix, ieiy+ieiy/2+100);
    t++;
    if(t>=60){
    exportflag=false;
    }
  }
}

public void mousePressed()
{
  export=get();

  if (ieix-ieiwidth/2<0) {
    ieix=ieix-(int)(ieix-ieiwidth/2);
  } else if (ieix+ieiwidth/2>width) {
    ieix=ieix+width-(int)(ieix+ieiwidth/2);
  }
  if (ieiy-ieiheight/2<0) {
    ieiy=ieiy-(int)(ieiy-ieiheight/2);
  } else if (ieiy+ieiheight/2>height) {
    ieiy=ieiy+height-(int)(ieiy+ieiheight/2);
  }


  export=export.get(ieix-(int)ieiwidth/2, ieiy-(int)ieiheight/2, (int)ieiwidth, (int)ieiheight);
  // int a=(int)random(10000);
  int a=0;
  export.save("export"+a+".jpg");

  t=0;
  exportflag=true;
}

public void keyPressed() {
  if (key == CODED) {      // コード化されているキーが押された
    if (keyCode == UP) {    // キーコードを判定
      ieiwidth+=3.55f;
      ieiheight+=4;
    } else if (keyCode == DOWN) {
      ieiwidth-=3.55f;
      ieiheight-=4;
    }
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ieimaker" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
