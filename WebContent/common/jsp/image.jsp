<%@ page contentType="image/jpeg" import="java.awt.*, 
java.awt.image.*,java.util.*,javax.imageio.*,java.util.List,java.util.ArrayList" %> 
<%! 
Color getRandColor(int fc,int bc) 
{ 
Random random = new Random(); 
if(fc>255) fc=255; 
if(bc>255) bc=255; 
int r=fc+random.nextInt(bc-fc); 
int g=fc+random.nextInt(bc-fc); 
int b=fc+random.nextInt(bc-fc); 
return new Color(r,g,b); 
} 
%> 
<% 
out.clear();
out = pageContext.pushBody();
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 

char returnstr;
Random random = new Random(); 
List<Integer> randomlist = new ArrayList<Integer>();
for (int i = 50; i <= 57; i++) {
		randomlist.add(i);

}

for (int i = 65; i <= 90; i++) {
	if(73 != i){
		randomlist.add(i);
	}
}

for (int i = 97; i <= 122; i++) {
	if(105 != i && 108 != i && 111 != i){
		randomlist.add(i);
	}
}
int width=60, height=20; 
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
Graphics g = image.getGraphics(); 
g.setColor(getRandColor(200,250)); 
g.fillRect(0, 0, width, height); 
g.setFont(new Font("Times New Roman",Font.PLAIN,18)); 
g.setColor(getRandColor(160,200)); 
for (int i=0;i<155;i++) 
{ 
int x = random.nextInt(width); 
int y = random.nextInt(height); 
int xl = random.nextInt(12); 
int yl = random.nextInt(12); 
g.drawLine(x,y,x+xl,y+yl); 
} 
String sRand = ""; 
for (int i=0;i<4;i++){ 
int result = randomlist.get(random.nextInt(56));
String rand=String.valueOf((char)result); 
sRand+=rand; 
g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110))); 
g.drawString(rand,13*i+6,16); 
} 
for(int i=0;i<4;i++){       
 g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));    
 g.drawLine(random.nextInt(60), random.nextInt(30), random.nextInt(60), random.nextInt(30));
}
session.setAttribute("rand",sRand); 
g.dispose(); 
ImageIO.write(image, "JPEG", response.getOutputStream()); 
%>
