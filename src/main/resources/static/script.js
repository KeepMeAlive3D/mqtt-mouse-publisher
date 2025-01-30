const ws = new WebSocket(`${window.location.href}/ws`);

let wsOpen = false
ws.onopen = () => {
    wsOpen = true
}
ws.onclose = () => {
    wsOpen = false
}

document.addEventListener('mousemove', function (event) {
    if (!wsOpen) return
    console.log('Mouse X:', event.clientX, 'Mouse Y:', event.clientY);
    const element = document.elementFromPoint(event.clientX, event.clientY);

    ws.send(`x:${event.clientX}`)
    ws.send(`y:${event.clientY}`)
});