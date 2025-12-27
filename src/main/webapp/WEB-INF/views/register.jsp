<%@ page contentType="text/html;charset=UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>NovaBank | Register</title>
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
                    <span class="badge">Safe • Secure • Fast</span>
                </div>
            </div>

            <div class="hero">
                <div class="hero-left card">
                    <h1 class="h1">Join NovaBank</h1>
                    <div class="kicker">
                        Create an account to start managing your finances securely.
                        <div class="divider"></div>
                        <div class="table-ish">
                            <div class="item">
                                <b>Smart Banking</b>
                                <span>Real-time transfers</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="hero-right card">
                    <h2 class="h2">Create Account</h2>
                    <div class="kicker">Enter your details to register.</div>

                    <form method="post" action="${pageContext.request.contextPath}/register">
                        <label class="label">Full Name</label>
                        <input class="input" name="fullname" required>

                        <label class="label">Username</label>
                        <input class="input" name="username" autocomplete="username" required>

                        <label class="label">Password</label>
                        <input class="input" type="password" name="password" required>

                        <button class="btn" type="submit">Sign Up</button>

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
                        Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in here</a>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>