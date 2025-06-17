const express = require('express');
const fs = require('fs');
const path = require('path');

const DATA_DIR = 'aedb_data';

const app = express();
app.use(express.json());

const hashtable = {};

// Ensure data directory exists
if (!fs.existsSync(DATA_DIR)) {
    fs.mkdirSync(DATA_DIR);
}

// Store in-memory
app.post('/memory/:key', (req, res) => {
    hashtable[req.params.key] = req.body.data;
    res.json({ status: 'ok' });
});

// Retrieve from memory
app.get('/memory/:key', (req, res) => {
    const key = req.params.key;
    if (key in hashtable) {
        res.send(hashtable[key]);
    } else {
        res.send('null');
    }
});

// Store on disk
app.post('/disk/:key', (req, res) => {
    const destinationFile = path.join(DATA_DIR, req.params.key);
    fs.writeFileSync(destinationFile, req.body.data);
    res.json({ status: 'ok' });
});

// Retrieve from disk
app.get('/disk/:key', (req, res) => {
    const destinationFile = path.join(DATA_DIR, req.params.key);
    try {
        const data = fs.readFileSync(destinationFile, 'utf8');
        res.send(data);
    } catch (e) {
        res.send('null');
    }
});

app.listen(3001, () => {
    console.log('Listening on port 3001!');
});
