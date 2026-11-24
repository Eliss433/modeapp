import { useEffect, useRef, useState } from "react";

export default function Home() {
    const videoRef = useRef<HTMLVideoElement>(null);
    const streamRef = useRef<MediaStream | null>(null);

    const [status, setStatus] = useState<"aus" | "startet" | "an">("aus");
    const [meldung, setMeldung] = useState("");

    async function kameraOeffnen() {
        setStatus("startet");
        setMeldung("");

        try {
            const stream = await navigator.mediaDevices.getUserMedia({
                video: true,
                audio: false,
            });

            if (!videoRef.current) {
                stream.getTracks().forEach(track => track.stop());
                return;
            }

            streamRef.current = stream;
            videoRef.current.srcObject = stream;
            setStatus("an");
        } catch {
            setMeldung(
                "Kamera konnte nicht geöffnet werden. Prüfe die Kameraberechtigung."
            );
            setStatus("aus");
        }
    }

    function kameraSchliessen() {
        streamRef.current?.getTracks().forEach(track => track.stop());
        streamRef.current = null;

        if (videoRef.current) {
            videoRef.current.srcObject = null;
        }

        setStatus("aus");
    }

    // Kamera ausschalten, wenn Home verlassen wird
    useEffect(() => {
        return () => {
            streamRef.current?.getTracks().forEach(track => track.stop());
        };
    }, []);

    return (
        <main>
            <button onClick={kameraOeffnen} disabled={status !== "aus"}>
                Kamera öffnen
            </button>

            <button onClick={kameraSchliessen} disabled={status !== "an"}>
                Kamera schließen
            </button>

            <video
                ref={videoRef}
                autoPlay
                playsInline
                muted
                style={{ width: "100%", maxWidth: "400px" }}
            />

            <p>{meldung}</p>
        </main>
    );
}