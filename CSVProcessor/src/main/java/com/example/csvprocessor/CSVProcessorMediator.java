package com.example.csvprocessor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseLog;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.util.PayloadHelper;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class CSVProcessorMediator extends AbstractMediator {

	public boolean mediate(MessageContext mc) {

		final SynapseLog logger = getLog(mc);

		// Read the CSV payload
		String csvPayload = PayloadHelper.getTextPayload(mc);

		// Rad CSV
		CSVReader reader = new CSVReaderBuilder(new StringReader(csvPayload)).withSkipLines(1).build();

		try {
			// Process CSV
			List<String[]> csvRows = reader.readAll();
			csvRows = csvRows.stream().map(row -> {
				String[] processedRow = row.clone();
				processedRow[1] = row[1].toUpperCase();
				return processedRow;
			}).collect(Collectors.toList());

			StringWriter stringWriter = new StringWriter();
			CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

			csvWriter.writeAll(csvRows);
			try {
				csvWriter.close();
				stringWriter.flush();
				String resultPayload = stringWriter.toString();

				// Set processed payload
				PayloadHelper.setTextPayload(mc, resultPayload);
			} catch (IOException e) {
				logger.error(e);
				return false;
			}
		} catch (IOException e) {
			logger.error(e);
			return false;
		}

		return true;
	}
}
