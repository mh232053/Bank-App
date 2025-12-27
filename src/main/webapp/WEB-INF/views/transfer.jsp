<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page import="com.bank.model.User" %>
        <%@ page import="java.math.BigDecimal" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>NovaBank | Transfer</title>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
            </head>

            <body>
                <div class="container">
                    <% User user=(User) session.getAttribute("user"); BigDecimal balance=(BigDecimal)
                        request.getAttribute("balance"); String error=(String) request.getAttribute("error"); String
                        success=(String) request.getAttribute("success"); %>

                        <div class="nav">
                            <div class="brand">
                                <div class="logo"></div>
                                <div>
                                    NovaBank
                                    <small>Secure Transfers</small>
                                </div>
                            </div>
                            <div class="nav-links">
                                <a class="pill" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                                <a class="pill active" href="${pageContext.request.contextPath}/transfer">Transfer</a>
                                <a class="pill" href="${pageContext.request.contextPath}/logout">Logout</a>
                            </div>
                        </div>

                        <div class="hero">
                            <div class="hero-left card">
                                <div class="row">
                                    <div>
                                        <h1 class="h1">Send Money</h1>
                                        <div class="kicker">Transfer funds between accounts with validation & session
                                            security.</div>
                                    </div>
                                    <span class="badge">Available: <b>PKR <%= balance %></b></span>
                                </div>

                                <div class="divider"></div>

                                <form method="post" action="${pageContext.request.contextPath}/transfer">
                                    <div class="grid">
                                        <div>
                                            <label class="label">Receiver User ID</label>
                                            <input class="input" name="toUserId" placeholder="Account ID"
                                                inputmode="numeric" required>
                                        </div>
                                        <div>
                                            <label class="label">Amount (PKR)</label>
                                            <input class="input" name="amount" placeholder="0.00" inputmode="decimal"
                                                required>
                                        </div>
                                    </div>

                                    <button class="btn" type="submit">Confirm Transfer</button>
                                    <a class="btn secondary" href="${pageContext.request.contextPath}/dashboard">Back to
                                        Dashboard</a>

                                    <% if (error !=null) { %>
                                        <div class="alert error">
                                            <%= error %>
                                        </div>
                                        <% } %>

                                            <% if (success !=null) { %>
                                                <div class="alert success">
                                                    <%= success %>
                                                </div>
                                                <% } %>
                                </form>
                            </div>

                            <div class="hero-right card">
                                <h2 class="h2">Transfer checklist</h2>
                                <div class="kicker">We validate every transaction</div>
                                <div class="divider"></div>

                                <div class="table-ish">
                                    <div class="item">
                                        <b>Receiver exists</b>
                                        <span>Valid user ID required</span>
                                    </div>
                                    <div class="item">
                                        <b>Amount > 0</b>
                                        <span>No negative / zero</span>
                                    </div>
                                    <div class="item">
                                        <b>Sufficient funds</b>
                                        <span>Must not exceed balance</span>
                                    </div>
                                    <div class="item">
                                        <b>No self-transfer</b>
                                        <span>Sender ≠ receiver</span>
                                    </div>
                                </div>

                                <div class="footer-note">
                                    Security note: transfer route is protected by an auth filter.
                                </div>
                            </div>
                        </div>
                </div>
            </body>

            </html>