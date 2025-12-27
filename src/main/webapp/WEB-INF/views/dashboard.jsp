<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ page import="com.bank.model.User" %>
        <%@ page import="java.math.BigDecimal" %>
            <%@ page import="java.util.List" %>
                <%@ page import="java.util.Map" %>

                    <!DOCTYPE html>
                    <html>

                    <head>
                        <title>NovaBank | Dashboard</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1" />
                        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles.css">
                    </head>

                    <body>
                        <div class="container">

                            <% User user=(User) session.getAttribute("user"); BigDecimal balance=(BigDecimal)
                                request.getAttribute("balance"); List<Map<String, Object>> transactions = (List
                                <Map<String, Object>>) request.getAttribute("transactions");

                                    if (balance == null) {
                                    balance = BigDecimal.ZERO;
                                    }
                                    %>

                                    <!-- NAV -->
                                    <div class="nav">
                                        <div class="brand">
                                            <div class="logo"></div>
                                            <div>
                                                NovaBank
                                                <small>Personal Banking</small>
                                            </div>
                                        </div>

                                        <div class="nav-links">
                                            <a class="pill active"
                                                href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                                            <a class="pill"
                                                href="${pageContext.request.contextPath}/transfer">Transfer</a>
                                            <a class="pill"
                                                href="${pageContext.request.contextPath}/deposit">Deposit</a>
                                            <a class="pill" href="${pageContext.request.contextPath}/logout">Logout</a>
                                        </div>
                                    </div>

                                    <!-- MAIN GRID -->
                                    <div class="hero">

                                        <!-- LEFT: Overview -->
                                        <div class="hero-left card">
                                            <div class="row">
                                                <div>
                                                    <h1 class="h1">Hello, <%= (user !=null ? user.getFullName()
                                                            : "Customer" ) %>
                                                    </h1>
                                                    <div class="kicker">Here’s your account snapshot for today.</div>
                                                </div>

                                                <% if (user !=null) { %>
                                                    <span class="badge">
                                                        User: <b>
                                                            <%= user.getUsername() %>
                                                        </b> • ID: <b>
                                                            <%= user.getId() %>
                                                        </b>
                                                    </span>
                                                    <% } %>
                                            </div>

                                            <div class="divider"></div>

                                            <div class="kicker muted">Available Balance</div>
                                            <div class="money"><small>PKR</small>
                                                <%= balance %>
                                            </div>

                                            <div class="divider"></div>

                                            <div class="row">
                                                <a class="btn" href="${pageContext.request.contextPath}/transfer">Make a
                                                    Transfer</a>
                                                <a class="btn secondary"
                                                    href="${pageContext.request.contextPath}/logout">Sign out</a>
                                            </div>

                                            <div class="footer-note">
                                                Security note: this dashboard is protected by session authentication.
                                            </div>
                                        </div>

                                        <!-- RIGHT: Recent Transactions -->
                                        <div class="hero-right card">
                                            <h2 class="h2">Recent Transactions</h2>
                                            <div class="kicker">Last 10 activities</div>
                                            <div class="divider"></div>

                                            <div class="table-ish">

                                                <% if (transactions==null || transactions.isEmpty()) { %>
                                                    <div class="item"
                                                        style="justify-content:center; color: var(--muted2); padding: 32px 0;">
                                                        No transactions to display.
                                                    </div>
                                                    <% } else { String myUsername=(user !=null ? user.getUsername() : ""
                                                        ); for (Map<String, Object> tx : transactions) {

                                                        String sender = (tx.get("sender") != null) ?
                                                        tx.get("sender").toString() : "";
                                                        String receiver = (tx.get("receiver") != null) ?
                                                        tx.get("receiver").toString() : "";
                                                        String dateStr = (tx.get("date") != null) ?
                                                        tx.get("date").toString() : "";

                                                        BigDecimal amount = BigDecimal.ZERO;
                                                        Object amtObj = tx.get("amount");
                                                        if (amtObj instanceof BigDecimal) {
                                                        amount = (BigDecimal) amtObj;
                                                        } else if (amtObj != null) {
                                                        try { amount = new BigDecimal(amtObj.toString()); } catch
                                                        (Exception ignored) {}
                                                        }

                                                        boolean isDebit = (myUsername != null && !myUsername.isEmpty()
                                                        && sender.equalsIgnoreCase(myUsername));
                                                        String title = isDebit ? ("To: " + receiver) : ("From: " +
                                                        sender);

                                                        String amountColor = isDebit ? "var(--danger)" :
                                                        "var(--success)";
                                                        String sign = isDebit ? "-" : "+";
                                                        %>

                                                        <div class="item">
                                                            <div>
                                                                <b>
                                                                    <%= title %>
                                                                </b>
                                                                <div
                                                                    style="font-size:11px; color: var(--muted2); margin-top:4px;">
                                                                    <%= dateStr %>
                                                                </div>
                                                            </div>

                                                            <span style="font-weight:800; color: <%= amountColor %>;">
                                                                <%= sign %>
                                                                    <%= amount %>
                                                            </span>
                                                        </div>

                                                        <% } } %>

                                            </div>

                                            <div class="footer-note">
                                                Tip: if you want, we can add a “View all” page later (still optional).
                                            </div>
                                        </div>

                                    </div>
                        </div>
                    </body>

                    </html>