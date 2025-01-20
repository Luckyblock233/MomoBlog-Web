<%@ page contentType="image/jpeg;charset=UTF-8" language="java" pageEncoding="UTF-8"
         import="java.awt.*,java.awt.image.BufferedImage,java.util.Random,javax.imageio.ImageIO" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    final int width = 70;
    final int height = 30;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    Graphics g = image.getGraphics();
    try {
        g.setColor(new Color(220, 220, 220));
        g.fillRect(0, 0, width, height);

        Random random = new Random();
        int codeLength = 4; // 验证码长度
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            codeBuilder.append(random.nextInt(10));
        }
        String captchaCode = codeBuilder.toString();

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("captchaCode", captchaCode);
        httpSession.setMaxInactiveInterval(60);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString(captchaCode, 10, 22);

        g.setColor(new Color(150, 150, 150));
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 1, 1);
        }
        ImageIO.write(image, "JPEG", response.getOutputStream());
    } finally {
        g.dispose();
    }
%>
