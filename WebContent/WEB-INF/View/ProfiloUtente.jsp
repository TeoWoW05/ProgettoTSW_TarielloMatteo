<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="Model.Utente, Model.Ordine, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Il mio Profilo - Piece B Piece</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/General.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/ProfiloUtente.css">
</head>
<body>

	<%@ include file="Header.jsp"%>

	<%
	Utente utente = (Utente) session.getAttribute("utente");
	ArrayList<Ordine> ordini = (ArrayList<Ordine>) request.getAttribute("ordini");
	%>
	<div class="page-content">
		<div class="profilo-container">
			<h1>👤 Il mio Profilo</h1>

			<!-- Messaggio successo -->
			<%
			String messaggio = (String) session.getAttribute("messaggioSuccesso");
			if (messaggio != null) {
			%>
			<div class="message message-success">
				<span>✅</span>
				<%=messaggio%>
			</div>
			<%
			session.removeAttribute("messaggioSuccesso");
			}

			// Messaggio errore generico
			String errore = (String) session.getAttribute("errore");
			if (errore != null) {
			%>
			<div class="message message-error">
				<span>⚠️</span>
				<%=errore%>
			</div>
			<%
			session.removeAttribute("errore");
			}
			%>

			<div class="profilo-grid">

				<!-- Dati Personali (SOLO LETTURA) -->
				<div class="profilo-card">
					<h2>📋 Dati Personali</h2>

					<div class="info-list">
						<div class="info-row">
							<span class="info-label">Nome</span> <span class="info-value"><%=utente.getName()%></span>
						</div>

						<div class="info-row">
							<span class="info-label">Cognome</span> <span class="info-value"><%=utente.getCognome()%></span>
						</div>

						<div class="info-row">
							<span class="info-label">Email</span> <span class="info-value"><%=utente.getEmail()%></span>
						</div>

						<div class="info-row">
							<span class="info-label">Nickname</span> <span class="info-value"><%=utente.getNickname()%></span>
						</div>

						<div class="info-row">
							<span class="info-label">Ruolo</span> <span class="info-value">
								<%=utente.isAdmin() ? "🔧 Amministratore" : "👤 Utente"%>
							</span>
						</div>
					</div>
				</div>

				<%
				if (!utente.isAdmin()) {
				%>

				<!-- Cambia Password -->
				<div class="profilo-card">
					<h2>🔒 Cambia Password</h2>

					<%
					// Messaggi specifici per la password
					String successoPassword = (String) session.getAttribute("successoPassword");
					if (successoPassword != null) {
					%>
					<div class="message message-success">
						<span>✅</span>
						<%=successoPassword%>
					</div>
					<%
					session.removeAttribute("successoPassword");
					}

					String errorePassword = (String) session.getAttribute("errorePassword");
					if (errorePassword != null) {
					%>
					<div class="message message-error">
						<span>⚠️</span>
						<%=errorePassword%>
					</div>
					<%
					session.removeAttribute("errorePassword");
					}
					%>



					<form
						action="${pageContext.request.contextPath}/ProfiloUtenteServlet"
						method="post" class="profilo-form">
						<input type="hidden" name="action" value="cambiaPassword">

						<div class="form-group">
							<label for="vecchiaPassword">Password attuale</label> <input
								type="password" id="vecchiaPassword" name="vecchiaPassword"
								placeholder="Inserisci la password attuale">
						</div>

						<div class="form-group">
							<label for="nuovaPassword">Nuova password</label> <input
								type="password" id="nuovaPassword" name="nuovaPassword"
								placeholder="Minimo 6 caratteri">
						</div>

						<div class="form-group">
							<label for="confermaPassword">Conferma nuova password</label> <input
								type="password" id="confermaPassword" name="confermaPassword"
								placeholder="Ripeti la nuova password">
						</div>

						<button type="submit" class="btn-primary">🔐 Cambia
							Password</button>
					</form>

				</div>
				<%
				}
				%>

				<%
				if (utente.isAdmin()) {
				%>
				<div class="profilo-card profilo-card-ordini">
					<h2>📦 Gestione Ordini</h2>
					<p style="color: #ccc; margin-bottom: 20px;">Visualizza e
						filtra tutti gli ordini dei clienti.</p>
					<a href="${pageContext.request.contextPath}/ControlloOrdiniServlet"
						class="btn-primary"> 📋 Vai alla gestione ordini </a>
				</div>
				<%
				}
				%>
				<%
				if (!utente.isAdmin()) {
				%>
				<!-- Storico Ordini -->
				<div class="profilo-card profilo-card-ordini">
					<h2>📦 I miei Ordini</h2>

					<%
					if (ordini != null && !ordini.isEmpty()) {
					%>
					<div class="ordini-list">
						<%
						for (Ordine ordine : ordini) {
						%>
						<div class="ordine-item">
							<div class="ordine-header">
								<span class="ordine-id">Ordine #<%=ordine.getId()%></span> <span
									class="ordine-data"><%=ordine.getDataFormattata()%></span> <span
									class="ordine-stato stato-<%=ordine.getStato().toLowerCase().replace(" ", "-")%>">
									<%=ordine.getStato()%>
								</span>
							</div>

							<div class="ordine-details">
								<div class="ordine-info">
									<p>
										<strong>Totale:</strong>
										<%=ordine.getPrezzoFormattato()%></p>
									<p>
										<strong>Indirizzo:</strong>
										<%=ordine.getIndirizzoCompleto()%></p>
									<p>
										<strong>Pagamento:</strong> Carta
										<%=ordine.getCartaFormattata()%></p>
								</div>
							</div>
						</div>
						<%
						}
						%>
					</div>
					<%
					} else {
					%>
					<div class="ordini-vuoti">
						<p>🛒 Nessun ordine effettuato</p>
						<a href="${pageContext.request.contextPath}/ProdottiServlet"
							class="btn-link">Vai allo shopping</a>
					</div>
					<%
					}
					%>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<%@ include file="Footer.jsp"%>

</body>
</html>