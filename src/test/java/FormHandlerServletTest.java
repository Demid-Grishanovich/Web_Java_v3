

import com.example.v3.controller.FormHandlerServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@ExtendWith(MockitoExtension.class)
public class FormHandlerServletTest {

    @InjectMocks
    private FormHandlerServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter responseWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        responseWriter = new StringWriter();
        writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoPostWithValidData() throws ServletException, IOException {
        // Настройка
        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("email")).thenReturn("test@example.com");

        // Выполнение
        servlet.doPost(request, response);

        // Проверка
        verify(response).sendRedirect(anyString());
        writer.flush();
        assertTrue(responseWriter.toString().contains("success"));
    }

    @Test
    public void testDoPostWithMissingUsername() throws ServletException, IOException {
        // Настройка
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("email")).thenReturn("test@example.com");

        // Выполнение
        servlet.doPost(request, response);

        // Проверка
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Username is required");
    }

    @Test
    public void testDoPostWithMissingEmail() throws ServletException, IOException {
        // Настройка
        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("email")).thenReturn(null);

        // Выполнение
        servlet.doPost(request, response);

        // Проверка
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required");
    }

    @Test
    public void testDoPostWithInvalidEmail() throws ServletException, IOException {
        // Настройка
        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("email")).thenReturn("not_an_email");

        // Выполнение
        servlet.doPost(request, response);

        // Проверка
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid email format");
    }
}
