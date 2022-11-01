package com.chatbot.contentservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.azure.cosmos.models.PartitionKey;
import com.nimbusds.oauth2.sdk.util.StringUtils;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/dialogs")
public class DialogController {

	@Autowired
	private DialogRepository dialogs;

	public DialogController() throws Exception {
	}

	// Upsert - create if not exists, update if exists
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dialog> create(@RequestBody Dialog dialog) {

		System.out.println("add/update " + dialog);

		Dialog savedDialog = dialogs.save(dialog);
		return new ResponseEntity<>(savedDialog, HttpStatus.CREATED);
	}

	/**
	 * Returns distinct languages that are supported.
	 * 
	 * @return distinct languages
	 */
	@GetMapping(path = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String[]> getDistinctLanguages() {
		System.out.println("listing all distinct languages...");

		List<Dialog> result = new ArrayList<>();
		dialogs.findAll().iterator().forEachRemaining(result::add);

		String[] languages = result.stream().map(Dialog::getLanguage).distinct().toArray(String[]::new);

		return new ResponseEntity<String[]>(languages, HttpStatus.OK);
	}

	/**
	 * @param language
	 * @param dialogId
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Dialog>> getDialogs(@RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "dialogId", required = false) String dialogId) {
		System.out.println("listing all dialogs...");

		if (StringUtils.isBlank(language) && StringUtils.isBlank(dialogId)) {
			List<Dialog> result = new ArrayList<>();
			dialogs.findAll().iterator().forEachRemaining(result::add);
			System.out.println(result);
			return new ResponseEntity<List<Dialog>>(result, HttpStatus.OK);
		} else if (StringUtils.isNotBlank(language) && StringUtils.isBlank(dialogId)) {
			List<Dialog> result = dialogs.findByLanguage(language);
			System.out.println(result);
			return new ResponseEntity<List<Dialog>>(result, HttpStatus.OK);

		} else if (StringUtils.isNotBlank(language) && StringUtils.isNotBlank(dialogId)) {
			List<Dialog> result = dialogs.findByLanguageAndDialogId(language, dialogId);
			System.out.println(result);
			return new ResponseEntity<List<Dialog>>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/dialogtext", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DialogText> getDialogText(@RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "dialogId", required = false) String dialogId,
			@RequestParam(value = "dialogSeqNumber", required = false) Integer dialogSeqNumber) {
		System.out.println("retrieving dialog text...");

		if (StringUtils.isNotBlank(language) && StringUtils.isNotBlank(dialogId)) {
			List<Dialog> result = dialogs.findByLanguageAndDialogId(language, dialogId);

			if (result != null & result.size() > 0) {
				Dialog dialog = result.get(0);
				List<DialogText> dialogTexts = dialog.getDialogTexts();
				System.out.println(dialogTexts.size());
				for (int i = 0; i < dialogTexts.size(); i++) {
					DialogText dialogText = dialogTexts.get(i);
					System.out.println(dialogText);
					if (dialogSeqNumber.equals(dialogText.getDialogSeqNumber())) {
						System.out.println(dialogText);
						return new ResponseEntity<DialogText>(dialogText, HttpStatus.OK);
					}
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/deliveryoptions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getDeliveryOptions(
			@RequestParam(value = "language", required = false) String language) {
		List<String> deliveryOptions = new ArrayList<String>();

		if (StringUtils.isNotBlank(language)) {
			List<Dialog> result = dialogs.findByLanguageAndDialogId(language, "delivery_options");

			if (result != null & result.size() > 0) {
				Dialog dialog = result.get(0);
				List<DialogText> dialogTexts = dialog.getDialogTexts();
				System.out.println(dialogTexts.size());
				for (int i = 0; i < dialogTexts.size(); i++) {
					deliveryOptions.add(dialogTexts.get(i).getDialogTextId() + "|" + dialogTexts.get(i).getDialogText() + "|" + dialogTexts.get(i).getDialogTextDescription() );
				}
				return new ResponseEntity<List<String>>(deliveryOptions, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	
	@GetMapping(path = "/deliveryoptions/random", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DeliveryOption>> getDeliveryOptionsRandom(
			@RequestParam(value = "language", required = false) String language) {
		List<String> deliveryOptions = new ArrayList<String>();
		List<DeliveryOption> deliveryOptionsList = new ArrayList<DeliveryOption>();

		if (StringUtils.isNotBlank(language)) {
			List<Dialog> result = dialogs.findByLanguageAndDialogId(language, "delivery_options");

			if (result != null & result.size() > 0) {
				Dialog dialog = result.get(0);
				List<DialogText> dialogTexts = dialog.getDialogTexts();
				System.out.println(dialogTexts.size());
				for (int i = 0; i < dialogTexts.size(); i++) {
					DeliveryOption deliveryOption = new DeliveryOption();
					deliveryOption.setValue(dialogTexts.get(i).getDialogTextId());
					deliveryOption.setAction(new Action("imBack", dialogTexts.get(i).getDialogText() + "|" + dialogTexts.get(i).getDialogTextDescription(), dialogTexts.get(i).getDialogTextId()));
					deliveryOptionsList.add(deliveryOption);
				}
				
				
				List<DeliveryOption> randomDeliveryOptionsList = pickRandomDeliveryOptions(deliveryOptionsList, getRandomNumber(1, 6));
				 
				return new ResponseEntity<List<DeliveryOption>>(randomDeliveryOptionsList, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	
	private static List<DeliveryOption> pickRandomDeliveryOptions(List<DeliveryOption> lst, int n) {
	    List<DeliveryOption> copy = new ArrayList<DeliveryOption>(lst);
	    Collections.shuffle(copy);
	    return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
	}
	
	private static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}

	// replace existing item (not upsert)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dialog> replace(@RequestBody Dialog dialog) {

		System.out.println("replacing dialog " + dialog.getLanguage());

		Optional<Dialog> maybe = dialogs.findById(dialog.getLanguage(), new PartitionKey(dialog.getLanguage()));
		if (!maybe.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Dialog savedDialog = dialogs.save(dialog);
		return new ResponseEntity<>(savedDialog, HttpStatus.OK);
	}

}