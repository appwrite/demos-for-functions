const fs = require('fs');

//function takes an array of objects and converts to stringified csv

module.exports = {
	generateCsv: (items) => {
		let csv = '';
		let keysCounter = 0;
		const keysAmount = Object.keys(items[0]).length;

		//generate headers for the csv
		for (let key in items[0]) {
			csv += key + (keysCounter + 1 < keysAmount ? ',' : '\r\n');
			keysCounter++;
		}

		// generate data items for csv
		for (let row = 0; row < items.length; row++) {
			keysCounter = 0;

			for (let key in items[row]) {
				csv += items[row][key] + (keysCounter + 1 < keysAmount ? ',' : '\r\n');
				keysCounter++;
			}
		}
		return csv;
	},
};
