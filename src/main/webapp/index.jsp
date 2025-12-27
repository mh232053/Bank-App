<%@ page contentType="text/html;charset=UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>NovaBank | Welcome</title>
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
                    <span class="badge">Next Gen Banking</span>
                </div>
            </div>

            <div class="hero">
                <div class="hero-left card">
                    <h1 class="h1">Banking Evolved.</h1>
                    <div class="kicker">
                        Experience the future of secure, fast, and reliable digital banking.
                        Manage your assets with confidence.
                    </div>
                    <div class="divider"></div>
                    <div class="table-ish">
                        <div class="item">
                            <b>Zero Fees</b>
                            <span>On all standard transfers</span>
                        </div>
                        <div class="item">
                            <b>Secure</b>
                            <span>Enterprise-grade encryption</span>
                        </div>
                        <div class="item">
                            <b>Fast</b>
                            <span>Instant processing</span>
                        </div>
                    </div>
                </div>

                <div class="hero-right card">
                    <h2 class="h2">Get Started</h2>
                    <div class="kicker">Choose an option to continue.</div>

                    <br>

                    <div class="label">New User?</div>
                    <a href="${pageContext.request.contextPath}/register" class="btn"
                        style="width:100%; text-align:center;">
                        Create Account
                    </a>

                    <br><br>

                    <div class="label">Existing User?</div>
                    <a href="${pageContext.request.contextPath}/login" class="btn secondary"
                        style="width:100%; text-align:center;">
                        Sign In
                    </a>

                    <div class="divider"></div>

                    <div class="footer-note">
                        By continuing, you agree to our Terms of Service and Privacy Policy.
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>