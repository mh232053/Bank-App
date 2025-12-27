<%@ page contentType="text/html;charset=UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>NovaBank | Sign in</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
    </head>

    <body>
        <div class="container">
            <div class="nav">
                <div class="brand">
                    <div class="logo"></div>
                    <div>
                        NovaBank
                        <small>Secure Online Banking</small>
                    </div>
                </div>
                <div class="nav-links">
                    <span class="badge">Jakarta • MVC • Tomcat 10</span>
                </div>
            </div>

            <div class="hero">
                <div class="hero-left card">
                    <h1 class="h1">Welcome back !</h1>
                    <div class="kicker">
                        Sign in to view your balance and make transfers securely.
                        <div class="divider"></div>
                        <div class="table-ish">
                            <div class="item">
                                <b>Secure Login</b>
                                <span>Standard Encryption</span>
                            </div>
                        </div>
                        <div class="footer-note">
                            Unauthorized access is prohibited.
                        </div>
                    </div>
                </div>

                <div class="hero-right card">
                    <h2 class="h2">Sign in</h2>
                    <div class="kicker">Enter your credentials to continue.</div>

                    <form method="post" action="${pageContext.request.contextPath}/login">
                        <label class="label">Username</label>
                        <input class="input" name="username" autocomplete="username" required>

                        <label class="label">Password</label>
                        <input class="input" type="password" name="password" placeholder="••••"
                            autocomplete="current-password" required>

                        <button class="btn" type="submit">Sign in</button>

                        <% String error=(String) request.getAttribute("error"); if (error !=null) { %>
                            <div class="alert error">
                                <%= error %>
                            </div>
                            <% } %>
                                <% String message=(String) request.getAttribute("message"); if (message !=null) { %>
                                    <div class="alert success">
                                        <%= message %>
                                    </div>
                                    <% } %>
                    </form>

                    <div class="footer-note">
                        New user? <a href="${pageContext.request.contextPath}/register">Sign up</a>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>
