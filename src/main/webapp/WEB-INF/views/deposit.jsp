<%@ page contentType="text/html;charset=UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>NovaBank | Deposit</title>
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
                    <a href="${pageContext.request.contextPath}/dashboard" class="pill">Dashboard</a>
                    <a href="${pageContext.request.contextPath}/logout" class="pill">Sign Out</a>
                </div>
            </div>

            <div class="hero">
                <div class="hero-left card">
                    <h1 class="h1">Deposit Funds</h1>
                    <div class="kicker">
                        Add funds to your account securely.
                        <div class="divider"></div>
                        <div class="money">
                            <small>Current Balance</small>
                            $<%= request.getAttribute("balance") %>
                        </div>
                    </div>
                </div>

                <div class="hero-right card">
                    <h2 class="h2">Make a Deposit</h2>
                    <div class="kicker">Enter amount to deposit.</div>

                    <form method="post" action="${pageContext.request.contextPath}/deposit">
                        <label class="label">Amount</label>
                        <input class="input" type="number" step="0.01" name="amount" placeholder="0.00" required>

                        <button class="btn" type="submit">Confirm Deposit</button>

                        <% String error=(String) request.getAttribute("error"); if (error !=null) { %>
                            <div class="alert error">
                                <%= error %>
                            </div>
                            <% } %>

                                <% String success=(String) request.getAttribute("success"); if (success !=null) { %>
                                    <div class="alert success">
                                        <%= success %>
                                    </div>
                                    <% } %>
                    </form>
                </div>
            </div>
        </div>
    </body>

    </html>