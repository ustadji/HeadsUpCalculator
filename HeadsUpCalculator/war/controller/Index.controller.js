sap.ui.define([ "sap/ui/core/mvc/Controller", "sap/m/MessageToast", "sap/ui/model/json/JSONModel", "sap/ui/model/resource/ResourceModel" ], function(
		Controller, MessageToast, JSONModel, ResourceModel) {
	"use strict";

	/**
	 * Global variables - I know its bad :(
	 */
	var pokerHandsSuitAndCard = {
			firstCard : {},
			secondCard : {},
			thirdCard : {},
			fourthCard : {}
	};

	var aCardValues = [ "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" ];
	var aSuitValues = [ "hearts", "diams", "clubs", "spades" ];
	var cardIdArray = [ "firstCard", "secondCard", "thirdCard", "fourthCard" ];

	var cardIndex = 0;
	return Controller.extend("poker.controller.Index", {

		onInit : function() {
			var that = this;

			var rowCount = 0;
			var oAllCardsPanel = this.getView().byId("allCardsPanel");
			var oSelectedCardsPanel = this.getView().byId("selectedCardsPanel");
			/**
			 * Display All Cards
			 */
			jQuery.each(aSuitValues, function(index1, suit) {
				var oHorizontalLayout = new sap.ui.layout.HorizontalLayout({});

				rowCount++;
				jQuery.each(aCardValues, function(index2, card) {
					var matrixCellId = suit + card + 'cell';
					var htmlId = suit + card + 'html';

					var oHtml = new sap.ui.core.HTML({
						id : htmlId,
						content : "<div class='smallCard'>" + card + " &" + suit + ";</div>",
						preferDOM : false
					});
					oHtml.attachBrowserEvent('click', function() {
						that._displayCard(card, suit);
					})
					oHorizontalLayout.addContent(oHtml);
				});
				oAllCardsPanel.addContent(oHorizontalLayout);
			});

			/**
			 * First Hand Percentage
			 */
			var oHorizontalLayout = new sap.ui.layout.HorizontalLayout({});
			var oFirstHandPercentageHtml = new sap.ui.core.HTML({
				id : 'firstHandPercentageHtml',
				// the static content as a long string literal
//				content : "<div class='vs'>100%</div>",
				preferDOM : true,
			});
			oHorizontalLayout.addContent(oFirstHandPercentageHtml);

			/**
			 * Display Selected Cards
			 */
			jQuery.each(cardIdArray, function(index, whichCard) {

				/**
				 * Insert 'vs' after second displayed card
				 */
				if (index == 2) {
					var oHtmlVs = new sap.ui.core.HTML({
						// the static content as a long string literal
						content : "<div class='vs'>VS</div>",
						preferDOM : true,
					});
					oHorizontalLayout.addContent(oHtmlVs);
				}
				;

				var oHtml = new sap.ui.core.HTML({
					id : whichCard + 'html',
					// the static content as a long string literal
					content : "<div class='selectedCard'></div>",
					preferDOM : true,
				}).attachBrowserEvent('click', function() {
					alert("selected " + this.getId());
				});
				oHorizontalLayout.addContent(oHtml);
			});
			/**
			 * Second Hand Percentage
			 */
			var oSecondHandPercentageHtml = new sap.ui.core.HTML({
				id : 'secondHandPercentageHtml',
//				content : "<div class='vs'>80%</div>",
				preferDOM : true,
			});
			oHorizontalLayout.addContent(oSecondHandPercentageHtml);
			oSelectedCardsPanel.addContent(oHorizontalLayout);
		},

		onShowHello : function() {
			// read msg from i18n model
			var oBundle = this.getView().getModel("i18n").getResourceBundle();
			var sRecipient = this.getView().getModel().getProperty("/recipient/name");
			// var sMsg = oBundle.getText("helloMsg", [sRecipient]);
			// show message
			MessageToast.show(sRecipient);
		},

		onClear : function() {
			var myCard = sap.ui.getCore().byId("firstCardhtml");

			myCard.setContent("<div class='selectedCard'></div>");

			pokerHandsSuitAndCard = {
					firstCard : {},
					secondCard : {},
					thirdCard : {},
					fourthCard : {}
			};
			cardIndex = 0;
			this._makeAllCardChoicesVisible();
			this._clearAllDisplayedCards();
			this._clearAllPercentages();
			this._clearMessage();
		},

		onEvaluate : function() {
			var that = this;

			sap.ui.core.BusyIndicator.show(0);
			jQuery.ajax({
				type : "GET",
				url : "EvaluatorController",
				dataType : "json",
				contentType : "application/json",
				data : {
        			firstCardSuit : pokerHandsSuitAndCard.firstCard.suit,
        			firstCardValue : pokerHandsSuitAndCard.firstCard.cardValue,
        			secondCardSuit : pokerHandsSuitAndCard.secondCard.suit,
        			secondCardValue : pokerHandsSuitAndCard.secondCard.cardValue,
        			thirdCardSuit : pokerHandsSuitAndCard.thirdCard.suit,
        			thirdCardValue : pokerHandsSuitAndCard.thirdCard.cardValue,
        			fourthCardSuit : pokerHandsSuitAndCard.fourthCard.suit,
        			fourthCardValue : pokerHandsSuitAndCard.fourthCard.cardValue
				}
			}).done(function(data, textStatus, jqXHR) {
//				var parsed = jQuery.parseJSON(data);
				sap.ui.core.BusyIndicator.hide();
				var success = data.success;
				var firstHandPercentage = data.firstHandPercentage;
				var secondHandPercentage = data.secondHandPercentage;

				var firstHandCssClass, secondHandCssClass;
				if (firstHandPercentage > secondHandPercentage) {
					firstHandCssClass = "percentage highlightHigherPercentage";
					secondHandCssClass = "percentage highlightLowerPercentage";
				} else if (firstHandPercentage < secondHandPercentage){
					firstHandCssClass = "percentage highlightLowerPercentage";
					secondHandCssClass = "percentage highlightHigherPercentage";
				} else {
					firstHandCssClass = "percentage highlightHigherPercentage";
					secondHandCssClass = "percentage highlightHigherPercentage";
				}

				var firstHandPercentageHtml = sap.ui.getCore().byId('firstHandPercentageHtml');
				var secondHandPercentageHtml = sap.ui.getCore().byId('secondHandPercentageHtml');

				if (success == true) {
					firstHandPercentageHtml.setContent("<div class='" + firstHandCssClass + "'>" + firstHandPercentage + "%</div>");
					secondHandPercentageHtml.setContent("<div class='" + secondHandCssClass + "'>" + secondHandPercentage + "%</div>");
					$("#" + firstHandPercentageHtml.getId()).hide().fadeIn(2000);
					$("#" + secondHandPercentageHtml.getId()).hide().fadeIn(2000);
				}

				var oEvaluationMessageStrip = that.getView().byId('evaluationMessageStrip');
				var id = oEvaluationMessageStrip.getId();
				if (data.result) {
					oEvaluationMessageStrip.setVisible(true);
					oEvaluationMessageStrip.setText(data.result);
				}

			}).fail(function(jqXHR, textStatus, errorThrown) {
				sap.ui.core.BusyIndicator.hide();
			});
		},

		_displayCard : function(card, suit) {
			var currentCardId = cardIdArray[cardIndex];

			var myCard = sap.ui.getCore().byId(currentCardId + 'html');

			myCard.setContent("<div class='selectedCard'>" + card + " &" + suit + ";</div>");

			/**
			 * Is there any card in the pokerHandsArray at the current index?  If so, return it back
			 * to the card pool
			 */
			if (pokerHandsSuitAndCard[currentCardId].cardValue !== undefined) {
				/**
				 * put the card back into the card pool
				 */
				var existingSuit = pokerHandsSuitAndCard[currentCardId].suit;
				var existingCardValue = pokerHandsSuitAndCard[currentCardId].cardValue;
				var myCardFromList = sap.ui.getCore().byId(existingSuit + existingCardValue + "html");
				myCardFromList.removeStyleClass('hide');
			}

			pokerHandsSuitAndCard[currentCardId].suit = suit;
			pokerHandsSuitAndCard[currentCardId].cardValue = card;

			/**
			 * Remove class styling from current cell
			 */
			var myCurrentCell = sap.ui.getCore().byId(currentCardId + "html");
			myCurrentCell.removeStyleClass('selectedCardHighlighted');

			cardIndex += 1;
			cardIndex %= cardIdArray.length;

			/**
			 * Add class styling to next cell in line
			 */
			var nextCardId = cardIdArray[cardIndex];
			var myCell = sap.ui.getCore().byId(nextCardId + "html");
			myCell.addStyleClass('selectedCardHighlighted');

			/**
			 * disable card from list of cards
			 */
			var myCardFromList = sap.ui.getCore().byId(suit + card + "html");
			myCardFromList.addStyleClass('hide');
		},

		_makeAllCardChoicesVisible : function() {
			jQuery.each(aSuitValues, function(index1, suit) {
				jQuery.each(aCardValues, function(index2, card) {
					var myCard = sap.ui.getCore().byId(suit + card + 'html');
					// console.log(myCard.getId());
					myCard.removeStyleClass('hide');
				});
			});
		},

		_clearAllDisplayedCards : function() {
			jQuery.each(cardIdArray, function(index, cardId) {
				var myDisplayedCard = sap.ui.getCore().byId(cardId + 'html');
				myDisplayedCard.setContent("<div class='selectedCard'></div>");
			});
		},

		_clearAllPercentages : function() {
			var firstPercentage = sap.ui.getCore().byId('firstHandPercentageHtml');
			firstPercentage.setContent("");
			var secondPercentage = sap.ui.getCore().byId('secondHandPercentageHtml');
			secondPercentage.setContent("");
		},

		_clearMessage : function() {
			var oEvaluationMessageStrip = this.getView().byId('evaluationMessageStrip');
			oEvaluationMessageStrip.setVisible(false);
		}


	});
});