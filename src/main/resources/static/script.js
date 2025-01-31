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

    const pixel = document.createElement("div");
    pixel.style.position = "absolute";
    pixel.style.left = `${event.pageX - 2.5}px`;
    pixel.style.top = `${event.pageY - 2.5}px`;
    pixel.style.width = "5px";
    pixel.style.height = "5px";
    pixel.style.backgroundColor = `hsl(${(event.pageX + event.pageY) % 360}, 100%, 50%)`;
    pixel.style.pointerEvents = "none";
    pixel.style.opacity = "1";
    pixel.style.borderRadius = "50%";
    pixel.style.transition = "opacity 0.5s ease-out";
    document.body.appendChild(pixel);

    setTimeout(() => {
        pixel.style.opacity = "0";
        setTimeout(() => pixel.remove(), 500);
    }, 50);
});