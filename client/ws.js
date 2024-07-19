const nb = document.querySelector('.name-block');
const ob = document.querySelector('.output-block');
const ib = document.querySelector('.input-block');

let ws = null, openedSession = false;

nb.addEventListener('blur', handleNameChange);

nb.addEventListener('keydown', (e) =>
{
    if (e.key === 'Enter')
        handleNameChange();
});

nb.addEventListener('focus', (e) =>
{
    nb.style.color = 'black';

    if (ws !== null)
    {
        openedSession = false;

        ws.close();
    }

    nb.value = '';
});


function handleNameChange ()
{
    if(openedSession === true)
        return;

    if (nb.value.length < 2)
    {
        nb.style.color = 'red';

        return;
    }

    openedSession = true;
    openSocket(nb.value)
}

function openSocket (username) {

    ws = new WebSocket(`ws://localhost:8080/chat/${username}`);

    ws.onopen = (wb, event) => {
        console.log(event);
    };

    ws.onerror = (event) => {
        console.log(event)
    }

    ws.onmessage = (e) => {
        const msgObj = JSON.parse(e.data);

        if (msgObj.from === '@special')
            console.log('todo')

        ob.innerHTML = msgObj.content
    }
}

ob.addEventListener('blur', handleNewMsg);
ob.addEventListener('keydown', (e) =>
{
    if(e.key === 'Enter')
        handleNewMsg();
});

function handleNewMsg ()
{
    console.log(ob.value)
}